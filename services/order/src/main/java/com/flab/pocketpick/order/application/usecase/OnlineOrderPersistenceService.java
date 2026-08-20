package com.flab.pocketpick.order.application.usecase;

import com.flab.pocketpick.order.application.dto.CreateOnlineOrderRequest;
import com.flab.pocketpick.order.application.dto.ShippingAddressRequest;
import com.flab.pocketpick.order.domain.order.OnlineOrder;
import com.flab.pocketpick.order.domain.order.vo.OnlineOrderPrice;
import com.flab.pocketpick.order.domain.order.vo.ShippingAddress;
import com.flab.pocketpick.order.global.properties.OrderPolicyProperties;
import com.flab.pocketpick.order.infra.persistence.OnlineOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnlineOrderPersistenceService {

    private final OnlineOrderRepository onlineOrderRepository;
    private final OrderPolicyProperties orderPolicyProperties;

    @Transactional
    public void save(Long customerId, CreateOnlineOrderRequest request, long itemPrice) {
        ShippingAddressRequest addr = request.shippingAddress();

        OnlineOrder order = OnlineOrder.builder()
                .postId(request.postId())
                .sellerId(request.sellerId())
                .customerId(customerId)
                .price(new OnlineOrderPrice(itemPrice, orderPolicyProperties.deliveryFee()))
                .shippingAddress(ShippingAddress.builder()
                        .zipCode(addr.zipCode())
                        .roadAddress(addr.roadAddress())
                        .detailAddress(addr.detailAddress())
                        .receiverName(addr.receiverName())
                        .receiverPhone(addr.receiverPhone())
                        .deliveryRequest(addr.deliveryRequest())
                        .build())
                .build();

        onlineOrderRepository.save(order);
    }
}
