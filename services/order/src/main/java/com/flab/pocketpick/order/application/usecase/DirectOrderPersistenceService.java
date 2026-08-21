package com.flab.pocketpick.order.application.usecase;

import com.flab.pocketpick.order.application.dto.CreateDirectOrderRequest;
import com.flab.pocketpick.order.application.dto.MeetingLocationRequest;
import com.flab.pocketpick.order.domain.order.DirectOrder;
import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.domain.order.event.DirectOrderCreatedEvent;
import com.flab.pocketpick.order.domain.order.vo.MeetingLocation;
import com.flab.pocketpick.order.infra.outbox.OutboxEventPublisher;
import com.flab.pocketpick.order.infra.persistence.DirectOrderRepository;
import com.flab.pocketpick.order.infra.persistence.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectOrderPersistenceService {

    private final DirectOrderRepository directOrderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final OutboxEventPublisher outboxEventPublisher;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void save(Long customerId, CreateDirectOrderRequest request, long itemPrice) {
        MeetingLocationRequest loc = request.meetingLocation();

        DirectOrder order = DirectOrder.builder()
                .postId(request.postId())
                .sellerId(request.sellerId())
                .customerId(customerId)
                .itemPrice(itemPrice)
                .meetingLocation(MeetingLocation.builder()
                        .zipCode(loc.zipCode())
                        .roadAddress(loc.roadAddress())
                        .detailAddress(loc.detailAddress())
                        .build())
                .meetingAt(request.meetingAt())
                .build();

        directOrderRepository.save(order);

        OutboxEvent outboxEvent = outboxEventRepository.save(outboxEventPublisher.createDirectOrderCreatedEvent(order));
        applicationEventPublisher.publishEvent(new DirectOrderCreatedEvent(outboxEvent));
    }
}
