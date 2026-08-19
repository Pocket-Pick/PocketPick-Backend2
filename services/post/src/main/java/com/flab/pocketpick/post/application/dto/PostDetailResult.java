package com.flab.pocketpick.post.application.dto;

import com.flab.pocketpick.post.domain.post.Post;
import com.flab.pocketpick.post.domain.post.PostStatus;
import com.flab.pocketpick.post.domain.post.TradeOption;

import java.util.List;

public record PostDetailResult(
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
    public static PostDetailResult of(Post post) {
        return new PostDetailResult(
                post.getId(),
                post.getSellerId(),
                post.getTitle(),
                post.getDescription(),
                post.getPrice(),
                post.getTradeOption(),
                post.getStatus(),
                0L,
                post.getCards().stream().map(CardResponse::from).toList(),
                post.getImages().stream().map(ImageResponse::from).toList()
        );
    }

    public static PostDetailResult of(PostDetailResult cached, Long viewCount) {
        return new PostDetailResult(
                cached.id(),
                cached.sellerId(),
                cached.title(),
                cached.description(),
                cached.price(),
                cached.tradeOption(),
                cached.status(),
                viewCount,
                cached.cards(),
                cached.images()
        );
    }
}
