package com.flab.pocketpick.order.domain.order.entity;

import com.flab.pocketpick.order.domain.order.enums.OutboxEventType;
import com.flab.pocketpick.order.domain.order.enums.OutboxStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;



@Entity
@Table(name = "outbox_events", indexes = {
        @Index(name = "idx_outbox_aggregate_seq", columnList = "aggregateId, sequenceNumber")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long aggregateId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OutboxEventType eventType;

    @Lob
    @Column(nullable = false)
    private byte[] payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OutboxStatus status;

    @Column(nullable = false)
    private int sequenceNumber;

    @Column(nullable = false)
    private int retryCount;

    @Column
    private LocalDateTime lastAttemptAt;

    @Column
    private LocalDateTime publishedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private OutboxEvent(Long aggregateId, OutboxEventType eventType, byte[] payload, int sequenceNumber) {
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = OutboxStatus.PENDING;
        this.sequenceNumber = sequenceNumber;
        this.retryCount = 0;
    }

    public void markPublished() {
        this.status = OutboxStatus.PUBLISHED;
        this.publishedAt = LocalDateTime.now();
    }

    public void markFailed(int maxRetryCount) {
        this.retryCount++;
        this.lastAttemptAt = LocalDateTime.now();
        if (this.retryCount >= maxRetryCount) {
            this.status = OutboxStatus.DEAD_LETTERED;
        } else {
            this.status = OutboxStatus.FAILED;
        }
    }
}
