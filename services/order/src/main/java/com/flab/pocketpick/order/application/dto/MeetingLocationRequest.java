package com.flab.pocketpick.order.application.dto;

import jakarta.validation.constraints.NotNull;

public record MeetingLocationRequest(
        @NotNull String zipCode,
        @NotNull String roadAddress,
        @NotNull String detailAddress
) {
}
