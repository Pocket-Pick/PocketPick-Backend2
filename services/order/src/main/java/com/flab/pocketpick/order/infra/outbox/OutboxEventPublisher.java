package com.flab.pocketpick.order.infra.outbox;

import com.flab.pocketpick.order.avro.DirectOrderCreatedEvent;
import com.flab.pocketpick.order.avro.MeetingLocation;
import com.flab.pocketpick.order.avro.OnlineOrderCreatedEvent;
import com.flab.pocketpick.order.avro.OnlineOrderPrice;
import com.flab.pocketpick.order.avro.ShippingAddress;
import com.flab.pocketpick.order.domain.order.DirectOrder;
import com.flab.pocketpick.order.domain.order.OnlineOrder;
import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.domain.order.enums.OutboxEventType;
import com.flab.pocketpick.order.infra.persistence.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final OutboxEventRepository outboxEventRepository;

    public OutboxEvent createOnlineOrderCreatedEvent(OnlineOrder order) {
        OnlineOrderCreatedEvent avroEvent = OnlineOrderCreatedEvent.newBuilder()
                .setOrderId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setSellerId(order.getSellerId())
                .setPostId(order.getPostId())
                .setPrice(OnlineOrderPrice.newBuilder()
                        .setItemPrice(order.getPrice().getItemPrice())
                        .setDeliveryFee(order.getPrice().getDeliveryFee())
                        .setTotalPrice(order.getPrice().getTotalPrice())
                        .build())
                .setShippingAddress(ShippingAddress.newBuilder()
                        .setZipCode(order.getShippingAddress().getZipCode())
                        .setRoadAddress(order.getShippingAddress().getRoadAddress())
                        .setDetailAddress(order.getShippingAddress().getDetailAddress())
                        .setReceiverName(order.getShippingAddress().getReceiverName())
                        .setReceiverPhone(order.getShippingAddress().getReceiverPhone())
                        .setDeliveryRequest(order.getShippingAddress().getDeliveryRequest())
                        .build())
                .build();

        return OutboxEvent.builder()
                .aggregateId(order.getId())
                .eventType(OutboxEventType.ONLINE_ORDER_CREATED)
                .sequenceNumber(outboxEventRepository.nextSequenceNumber(order.getId()))
                .payload(AvroSerializer.serialize(avroEvent, OnlineOrderCreatedEvent.getEncoder()::encode))
                .build();
    }

    public OutboxEvent createDirectOrderCreatedEvent(DirectOrder order) {
        DirectOrderCreatedEvent avroEvent = DirectOrderCreatedEvent.newBuilder()
                .setOrderId(order.getId())
                .setCustomerId(order.getCustomerId())
                .setSellerId(order.getSellerId())
                .setPostId(order.getPostId())
                .setItemPrice(order.getItemPrice())
                .setMeetingLocation(MeetingLocation.newBuilder()
                        .setZipCode(order.getMeetingLocation().getZipCode())
                        .setRoadAddress(order.getMeetingLocation().getRoadAddress())
                        .setDetailAddress(order.getMeetingLocation().getDetailAddress())
                        .build())
                .setMeetingAt(order.getMeetingAt().toString())
                .build();

        return OutboxEvent.builder()
                .aggregateId(order.getId())
                .eventType(OutboxEventType.DIRECT_ORDER_CREATED)
                .sequenceNumber(outboxEventRepository.nextSequenceNumber(order.getId()))
                .payload(AvroSerializer.serialize(avroEvent, DirectOrderCreatedEvent.getEncoder()::encode))
                .build();
    }
}
