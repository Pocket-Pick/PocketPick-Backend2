package com.flab.pocketpick.post.domain.post;

import com.flab.pocketpick.post.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_images")
public class PostImage extends BaseEntity {

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int order;

    @Builder
    public PostImage(String imageUrl, int order) {
        this.imageUrl = imageUrl;
        this.order = order;
    }
}
