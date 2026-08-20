package com.flab.pocketpick.order.domain.order.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingAddress {

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverPhone;

    private String deliveryRequest;

    public ShippingAddress(String zipCode, String roadAddress, String detailAddress,
                           String receiverName, String receiverPhone, String deliveryRequest) {
        this.zipCode = zipCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.deliveryRequest = deliveryRequest;
    }
}
