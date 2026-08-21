package com.flab.pocketpick.order.infra.relay;

import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.global.properties.OrderPolicyProperties;
import com.flab.pocketpick.order.infra.persistence.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OutboxEventUpdater {

    private final OutboxEventRepository outboxEventRepository;
    private final OrderPolicyProperties orderPolicyProperties;

    @Transactional
    public void markPublished(OutboxEvent event) {
        event.markPublished();
        outboxEventRepository.save(event);
    }

    @Transactional
    public void markFailed(OutboxEvent event) {
        event.markFailed(orderPolicyProperties.outboxMaxRetryCount());
        outboxEventRepository.save(event);
    }
}
