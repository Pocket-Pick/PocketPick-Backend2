package com.flab.pocketpick.order.global.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> postGrpcCircuitBreakerCustomizer() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
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
                "post-grpc"
        );
    }
}
