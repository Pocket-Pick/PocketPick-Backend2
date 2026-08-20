package com.flab.pocketpick.order.application.usecase;

import com.flab.pocketpick.order.application.dto.CreateOnlineOrderRequest;
import com.flab.pocketpick.order.infra.grpc.PostGrpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlineOrderService implements OnlineOrderUseCase {

    private final PostGrpcClient postGrpcClient;
    private final OnlineOrderPersistenceService onlineOrderPersistenceService;

    @Override
    public void createOnlineOrder(Long customerId, CreateOnlineOrderRequest request) {
        long itemPrice = postGrpcClient.getPostPrice(request.postId());
        onlineOrderPersistenceService.save(customerId, request, itemPrice);
    }
}
