package com.flab.pocketpick.post.infra.scheduler;

import com.flab.pocketpick.post.domain.post.Post;
import com.flab.pocketpick.post.infra.cache.PostViewCount;
import com.flab.pocketpick.post.infra.persistence.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewCountSyncService {

    private final PostJpaRepository postJpaRepository;

    @Transactional
    public void syncChunk(List<PostViewCount> chunk) {
        List<Long> postIds = chunk.stream().map(PostViewCount::postId).toList();
        Map<Long, Long> viewCountMap = chunk.stream()
                .collect(Collectors.toMap(PostViewCount::postId, PostViewCount::viewCount));

        List<Post> posts = postJpaRepository.findAllById(postIds);
        posts.forEach(post -> post.updateViewCount(viewCountMap.get(post.getId())));
    }
}
