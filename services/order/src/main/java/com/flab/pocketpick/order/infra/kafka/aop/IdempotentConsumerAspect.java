package com.flab.pocketpick.order.infra.kafka.aop;

import com.flab.pocketpick.order.domain.order.entity.InboxEvent;
import com.flab.pocketpick.order.infra.persistence.InboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentConsumerAspect {

    private final InboxEventRepository inboxEventRepository;
    private final TransactionTemplate transactionTemplate;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(idempotentConsumer)")
    public Object around(ProceedingJoinPoint pjp, IdempotentConsumer idempotentConsumer) throws Throwable {
        String inboxKey = resolveKey(pjp, idempotentConsumer.key());

        return transactionTemplate.execute(status -> {
            if (inboxEventRepository.existsByInboxKey(inboxKey)) {
                log.warn("[Inbox] 중복 메시지 무시: inboxKey={}", inboxKey);
                return null;
            }
            inboxEventRepository.save(InboxEvent.builder().inboxKey(inboxKey).build());
            try {
                return pjp.proceed();
            } catch (RuntimeException e) {
                status.setRollbackOnly();
                throw e;
            } catch (Throwable e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
    }

    private String resolveKey(ProceedingJoinPoint pjp, String expression) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        Object[] args = pjp.getArgs();
        EvaluationContext context = new StandardEvaluationContext();
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        return parser.parseExpression(expression).getValue(context, String.class);
    }
}
