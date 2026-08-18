package com.flab.pocketpick.user.domain.user.entity;

import com.flab.pocketpick.user.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String profileImageUrl;

    @Column(nullable = false)
    private String region;

    @Column
    private String introduce;

    @Builder
    private User(String nickname, String profileImageUrl, String region, String introduce) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.region = region;
        this.introduce = introduce;
    }
}
