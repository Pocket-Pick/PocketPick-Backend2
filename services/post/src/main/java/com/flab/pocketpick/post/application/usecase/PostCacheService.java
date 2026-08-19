package com.flab.pocketpick.post.application.usecase;

import com.flab.pocketpick.post.application.dto.PostDetailResult;
import com.flab.pocketpick.post.domain.post.Post;
import com.flab.pocketpick.post.domain.post.exception.PostNotFoundException;
import com.flab.pocketpick.post.infra.persistence.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCacheService {

    private final PostJpaRepository postJpaRepository;

    @Cacheable(cacheNames = "post", key = "#postId")
    public PostDetailResult getPostCached(Long postId) {
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        return PostDetailResult.of(post);
    }
}
