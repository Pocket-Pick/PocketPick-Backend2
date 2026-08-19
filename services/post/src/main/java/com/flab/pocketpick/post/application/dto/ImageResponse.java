package com.flab.pocketpick.post.application.dto;

import com.flab.pocketpick.post.domain.post.PostImage;

public record ImageResponse(
        Long id,
        String imageUrl,
        int order
) {
    public static ImageResponse from(PostImage image) {
        return new ImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getOrder()
        );
    }
}
