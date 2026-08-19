package com.flab.pocketpick.post.application.dto;

import com.flab.pocketpick.post.domain.post.PokemonType;
import com.flab.pocketpick.post.domain.post.Rarity;
import com.flab.pocketpick.post.domain.post.Supertype;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardRequest(
        @NotBlank String name,
        @NotNull Supertype supertype,
        Rarity rarity,
        PokemonType pokemonType
) {
}
