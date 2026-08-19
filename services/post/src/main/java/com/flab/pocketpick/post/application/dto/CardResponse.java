package com.flab.pocketpick.post.application.dto;

import com.flab.pocketpick.post.domain.post.PostCard;
import com.flab.pocketpick.post.domain.post.PokemonType;
import com.flab.pocketpick.post.domain.post.Rarity;
import com.flab.pocketpick.post.domain.post.Supertype;

public record CardResponse(
        Long id,
        String name,
        Supertype supertype,
        Rarity rarity,
        PokemonType pokemonType
) {
    public static CardResponse from(PostCard card) {
        return new CardResponse(
                card.getId(),
                card.getName(),
                card.getSupertype(),
                card.getRarity(),
                card.getPokemonType()
        );
    }
}
