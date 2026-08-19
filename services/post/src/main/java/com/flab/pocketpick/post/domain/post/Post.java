package com.flab.pocketpick.post.domain.post;

import com.flab.pocketpick.post.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseEntity {

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeOption tradeOption;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Column(nullable = false)
    private Long viewCount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostCard> cards;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostImage> images;

    @Builder
    public Post(Long sellerId, String title, String description, Long price, TradeOption tradeOption, PostStatus status, List<PostCard> cards, List<PostImage> images) {
        this.sellerId = sellerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.tradeOption = tradeOption;
        this.status = status;
        this.viewCount = 0L;
        this.cards = cards != null ? new ArrayList<>(cards) : new ArrayList<>();
        this.images = images != null ? new ArrayList<>(images) : new ArrayList<>();
    }
}
