package com.flab.pocketpick.order.domain.order;

import com.flab.pocketpick.order.domain.order.vo.MeetingLocation;
import com.flab.pocketpick.order.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "direct_orders")
public class DirectOrder extends BaseEntity {

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long itemPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DirectOrderStatus status;

    @Embedded
    private MeetingLocation meetingLocation;

    @Column(nullable = false)
    private LocalDateTime meetingAt;

    @Builder
    public DirectOrder(Long postId, Long sellerId, Long customerId, Long itemPrice,
                       MeetingLocation meetingLocation, LocalDateTime meetingAt) {
        this.postId = postId;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.itemPrice = itemPrice;
        this.status = DirectOrderStatus.CREATED;
        this.meetingLocation = meetingLocation;
        this.meetingAt = meetingAt;
    }
}
