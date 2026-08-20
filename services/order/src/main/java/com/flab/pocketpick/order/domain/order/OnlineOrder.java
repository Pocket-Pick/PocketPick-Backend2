package com.flab.pocketpick.order.domain.order;

import com.flab.pocketpick.order.domain.order.vo.OnlineOrderPrice;
import com.flab.pocketpick.order.domain.order.vo.ShippingAddress;
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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "online_orders")
public class OnlineOrder extends BaseEntity {

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Long customerId;

    @Embedded
    private OnlineOrderPrice price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnlineOrderStatus status;

    @Embedded
    private ShippingAddress shippingAddress;

    @Builder
    public OnlineOrder(Long postId, Long sellerId, Long customerId, OnlineOrderPrice price,
                       ShippingAddress shippingAddress) {
        this.postId = postId;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.price = price;
        this.status = OnlineOrderStatus.CREATED;
        this.shippingAddress = shippingAddress;
    }
}
