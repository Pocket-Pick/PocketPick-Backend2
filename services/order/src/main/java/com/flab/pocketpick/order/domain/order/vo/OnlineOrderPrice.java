package com.flab.pocketpick.order.domain.order.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnlineOrderPrice {

    @Column(nullable = false)
    private Long itemPrice;

    @Column(nullable = false)
    private Long deliveryFee;

    @Column(nullable = false)
    private Long totalPrice;

    public OnlineOrderPrice(Long itemPrice, Long deliveryFee) {
        this.itemPrice = itemPrice;
        this.deliveryFee = deliveryFee;
        this.totalPrice = itemPrice + deliveryFee;
    }
}
