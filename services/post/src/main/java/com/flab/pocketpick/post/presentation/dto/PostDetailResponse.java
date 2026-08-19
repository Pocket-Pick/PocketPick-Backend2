package com.flab.pocketpick.post.presentation.dto;

import com.flab.pocketpick.post.application.dto.CardResponse;
import com.flab.pocketpick.post.application.dto.ImageResponse;
import com.flab.pocketpick.post.application.dto.PostDetailResult;
import com.flab.pocketpick.post.domain.post.PostStatus;
import com.flab.pocketpick.post.domain.post.TradeOption;

import java.util.List;

public record PostDetailResponse(
        Long id,
        Long sellerId,
        String title,
        String description,
        Long price,
        TradeOption tradeOption,
        PostStatus status,
        Long viewCount,
        List<CardResponse> cards,
        List<ImageResponse> images
) {
    public static PostDetailResponse from(PostDetailResult result) {
        return new PostDetailResponse(
                result.id(),
                result.sellerId(),
                result.title(),
                result.description(),
                result.price(),
                result.tradeOption(),
                result.status(),
                result.viewCount(),
                result.cards(),
                result.images()
        );
    }
}
