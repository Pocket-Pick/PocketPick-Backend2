package com.flab.pocketpick.post.application.usecase;

import com.flab.pocketpick.post.application.dto.CreatePostRequest;
import com.flab.pocketpick.post.application.mapper.PostMapper;
import com.flab.pocketpick.post.infra.persistence.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService implements PostUseCase {

    private final PostJpaRepository postJpaRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public void create(CreatePostRequest request, Long sellerId) {
        postJpaRepository.save(postMapper.toEntity(request, sellerId));
    }
}
