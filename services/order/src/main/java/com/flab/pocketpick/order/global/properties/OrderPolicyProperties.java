package com.flab.pocketpick.order.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order.policy")
public record OrderPolicyProperties(
        long deliveryFee
) {
}
