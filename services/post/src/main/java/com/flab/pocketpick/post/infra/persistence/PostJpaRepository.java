package com.flab.pocketpick.post.infra.persistence;

import com.flab.pocketpick.post.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
