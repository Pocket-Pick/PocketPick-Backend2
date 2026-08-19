package com.flab.pocketpick.post.domain.post;

import com.flab.pocketpick.post.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_cards")
public class PostCard extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Supertype supertype;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Enumerated(EnumType.STRING)
    private PokemonType pokemonType;

    @Builder
    public PostCard(String name, Supertype supertype, Rarity rarity, PokemonType pokemonType) {
        this.name = name;
        this.supertype = supertype;
        this.rarity = rarity;
        this.pokemonType = pokemonType;
    }
}
