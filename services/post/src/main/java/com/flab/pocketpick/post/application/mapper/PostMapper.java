package com.flab.pocketpick.post.application.mapper;

import com.flab.pocketpick.post.application.dto.CardRequest;
import com.flab.pocketpick.post.application.dto.CreatePostRequest;
import com.flab.pocketpick.post.domain.post.Post;
import com.flab.pocketpick.post.domain.post.PostCard;
import com.flab.pocketpick.post.domain.post.PostImage;
import com.flab.pocketpick.post.domain.post.PostStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class PostMapper {

    public Post toEntity(CreatePostRequest request, Long sellerId) {
        List<String> imageUrls = request.images();
        return Post.builder()
                .sellerId(sellerId)
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .tradeOption(request.tradeOption())
                .status(PostStatus.AVAILABLE)
                .cards(request.cards().stream().map(this::toCardEntity).toList())
                .images(IntStream.range(0, imageUrls.size())
                        .mapToObj(i -> toImageEntity(imageUrls.get(i), i))
                        .toList())
                .build();
    }

    private PostCard toCardEntity(CardRequest request) {
        return PostCard.builder()
                .name(request.name())
                .supertype(request.supertype())
                .rarity(request.rarity())
                .pokemonType(request.pokemonType())
                .build();
    }

    private PostImage toImageEntity(String imageUrl, int order) {
        return PostImage.builder()
                .imageUrl(imageUrl)
                .order(order)
                .build();
    }
}
