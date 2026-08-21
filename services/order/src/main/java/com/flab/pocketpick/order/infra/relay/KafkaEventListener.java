package com.flab.pocketpick.order.infra.relay;

import com.flab.pocketpick.order.domain.order.event.DirectOrderCreatedEvent;
import com.flab.pocketpick.order.domain.order.event.OnlineOrderCreatedEvent;
import com.flab.pocketpick.order.infra.kafka.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class KafkaEventListener {

    private final OrderEventProducer orderEventProducer;
    private final OutboxEventUpdater outboxEventUpdater;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OnlineOrderCreatedEvent event) {
        orderEventProducer.publish(event.outboxEvent());
        outboxEventUpdater.markPublished(event.outboxEvent());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DirectOrderCreatedEvent event) {
        orderEventProducer.publish(event.outboxEvent());
        outboxEventUpdater.markPublished(event.outboxEvent());
    }
}
