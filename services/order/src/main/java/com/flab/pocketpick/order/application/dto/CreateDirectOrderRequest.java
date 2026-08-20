package com.flab.pocketpick.order.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateDirectOrderRequest(
        @NotNull Long postId,
        @NotNull Long sellerId,
        @Valid @NotNull MeetingLocationRequest meetingLocation,
        @NotNull LocalDateTime meetingAt
) {
}
