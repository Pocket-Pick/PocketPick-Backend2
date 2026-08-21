package com.flab.pocketpick.order.infra.kafka;

import com.flab.pocketpick.order.avro.DirectOrderCreatedEvent;
import com.flab.pocketpick.order.avro.OnlineOrderCreatedEvent;
import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.domain.order.enums.OutboxEventType;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private static final String ONLINE_ORDER_CREATED_TOPIC = "online-order-created";
    private static final String DIRECT_ORDER_CREATED_TOPIC = "direct-order-created";

    private final KafkaTemplate<String, GenericRecord> avroKafkaTemplate;

    public void publish(OutboxEvent event) {
        String topic = resolveTopic(event.getEventType());
        GenericRecord record = deserialize(event);
        avroKafkaTemplate.send(topic, String.valueOf(event.getAggregateId()), record);
    }

    private String resolveTopic(OutboxEventType eventType) {
        return switch (eventType) {
            case ONLINE_ORDER_CREATED -> ONLINE_ORDER_CREATED_TOPIC;
            case DIRECT_ORDER_CREATED -> DIRECT_ORDER_CREATED_TOPIC;
        };
    }

    private GenericRecord deserialize(OutboxEvent event) {
        try {
            return switch (event.getEventType()) {
                case ONLINE_ORDER_CREATED -> OnlineOrderCreatedEvent.getDecoder().decode(event.getPayload());
                case DIRECT_ORDER_CREATED -> DirectOrderCreatedEvent.getDecoder().decode(event.getPayload());
            };
        } catch (IOException e) {
            throw new SerializationException("Avro 역직렬화 실패: " + event.getEventType(), e);
        }
    }
}
