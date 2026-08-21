package com.flab.pocketpick.order.infra.relay;

import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.domain.order.enums.OutboxStatus;
import com.flab.pocketpick.order.infra.kafka.OrderEventProducer;
import com.flab.pocketpick.order.infra.persistence.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRelayService {

    private final OutboxEventRepository outboxEventRepository;
    private final OrderEventProducer orderEventProducer;
    private final OutboxEventUpdater outboxEventUpdater;

    @Scheduled(fixedDelay = 500)
    public void relay() {
        List<OutboxEvent> events = outboxEventRepository.findByStatusIn(
                List.of(OutboxStatus.PENDING, OutboxStatus.FAILED)
        );

        for (OutboxEvent event : events) {
            try {
                orderEventProducer.publish(event);
                outboxEventUpdater.markPublished(event);
            } catch (Exception e) {
                log.warn("[MessageRelayService] 발행 실패: eventId={}, retryCount={}", event.getId(), event.getRetryCount());
                outboxEventUpdater.markFailed(event);
            }
        }
    }
}
