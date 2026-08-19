package com.flab.pocketpick.user.global.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> authGrpcCircuitBreakerCustomizer() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(100)
                .minimumNumberOfCalls(50)
                .failureRateThreshold(80)
                .slowCallDurationThreshold(Duration.ofMillis(480))
                .slowCallRateThreshold(80)
                .waitDurationInOpenState(Duration.ofSeconds(3))
                .permittedNumberOfCallsInHalfOpenState(10)
                .recordException(throwable -> {
                    if (throwable instanceof StatusRuntimeException sre) {
                        Status.Code code = sre.getStatus().getCode();
                        return code == Status.Code.DEADLINE_EXCEEDED
                                || code == Status.Code.UNAVAILABLE
                                || code == Status.Code.INTERNAL;
                    }
                    return false;
                })
                .build();

        return factory -> factory.configure(
                builder -> builder.circuitBreakerConfig(config),
                "auth-grpc"
        );
    }
}
