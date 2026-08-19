package com.flab.pocketpick.post.application.usecase;

import com.flab.pocketpick.post.application.dto.CreatePostRequest;
import com.flab.pocketpick.post.application.dto.PostDetailResult;
import com.flab.pocketpick.post.application.mapper.PostMapper;
import com.flab.pocketpick.post.application.usecase.PostCacheService;
import com.flab.pocketpick.post.infra.cache.PostViewCountRepository;
import com.flab.pocketpick.post.infra.persistence.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {

    private final PostJpaRepository postJpaRepository;
    private final PostMapper postMapper;
    private final PostViewCountRepository postViewCountRepository;
    private final PostCacheService postCacheService;

    @Override
    @Transactional
    public void create(CreatePostRequest request, Long sellerId) {
        postJpaRepository.save(postMapper.toEntity(request, sellerId));
    }

    @Override
    public PostDetailResult getPost(Long postId) {
        Long viewCount = postViewCountRepository.increment(postId);
        PostDetailResult cached = postCacheService.getPostCached(postId);
        return PostDetailResult.of(cached, viewCount);
    }
}
