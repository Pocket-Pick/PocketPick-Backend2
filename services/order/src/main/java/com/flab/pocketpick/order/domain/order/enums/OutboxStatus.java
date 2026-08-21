package com.flab.pocketpick.order.domain.order.enums;

public enum OutboxStatus {
    PENDING,
    PUBLISHED,
    FAILED,
    DEAD_LETTERED
}
