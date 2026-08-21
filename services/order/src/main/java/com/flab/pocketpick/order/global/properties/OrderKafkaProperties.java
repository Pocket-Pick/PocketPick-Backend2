package com.flab.pocketpick.order.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record OrderKafkaProperties(
        String schemaRegistryUrl
) {
}
