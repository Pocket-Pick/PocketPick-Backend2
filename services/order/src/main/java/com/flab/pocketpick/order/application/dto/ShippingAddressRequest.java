package com.flab.pocketpick.order.application.dto;

import jakarta.validation.constraints.NotNull;

public record ShippingAddressRequest(
        @NotNull String zipCode,
        @NotNull String roadAddress,
        @NotNull String detailAddress,
        @NotNull String receiverName,
        @NotNull String receiverPhone,
        String deliveryRequest
) {
}
