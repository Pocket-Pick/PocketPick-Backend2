package com.flab.pocketpick.post.infra.scheduler;

import com.flab.pocketpick.post.infra.cache.PostViewCount;
import com.flab.pocketpick.post.infra.cache.PostViewCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewCountSyncScheduler {

    private static final long SYNC_INTERVAL_MS = 60_000;
    private static final int CHUNK_SIZE = 100;

    private final PostViewCountRepository postViewCountRepository;
    private final ViewCountSyncService viewCountSyncService;

    @Scheduled(fixedDelay = SYNC_INTERVAL_MS)
    public void sync() {
        List<PostViewCount> viewCounts = postViewCountRepository.getAll();
        if (viewCounts.isEmpty()) {
            return;
        }

        for (int start = 0; start < viewCounts.size(); start += CHUNK_SIZE) {
            int end = Math.min(start + CHUNK_SIZE, viewCounts.size());
            viewCountSyncService.syncChunk(viewCounts.subList(start, end));
        }

        log.info("[ViewCountSyncScheduler] {}개 게시글 조회수 동기화 완료", viewCounts.size());
    }
}
