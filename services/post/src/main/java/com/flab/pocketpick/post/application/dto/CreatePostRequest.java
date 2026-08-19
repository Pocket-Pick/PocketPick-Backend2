package com.flab.pocketpick.post.application.dto;

import com.flab.pocketpick.post.domain.post.TradeOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreatePostRequest(
        @NotBlank String title,
        String description,
        @NotNull @Positive Long price,
        @NotNull TradeOption tradeOption,
        @NotEmpty @Valid List<CardRequest> cards,
        @NotEmpty List<String> images
) {
}
