package com.flab.pocketpick.order.domain.order.event;

import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;

public record OnlineOrderCreatedEvent(OutboxEvent outboxEvent) {
}
