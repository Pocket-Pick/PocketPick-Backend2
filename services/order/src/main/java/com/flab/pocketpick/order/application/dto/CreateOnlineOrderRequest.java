package com.flab.pocketpick.order.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateOnlineOrderRequest(
        @NotNull Long postId,
        @NotNull Long sellerId,
        @Valid @NotNull ShippingAddressRequest shippingAddress
) {
}
