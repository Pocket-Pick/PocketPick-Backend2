package com.flab.pocketpick.order.application.usecase;

import com.flab.pocketpick.order.application.dto.CreateDirectOrderRequest;
import com.flab.pocketpick.order.infra.grpc.PostGrpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectOrderService implements DirectOrderUseCase {

    private final PostGrpcClient postGrpcClient;
    private final DirectOrderPersistenceService directOrderPersistenceService;

    @Override
    public void createDirectOrder(Long customerId, CreateDirectOrderRequest request) {
        long itemPrice = postGrpcClient.getPostPrice(request.postId());
        directOrderPersistenceService.save(customerId, request, itemPrice);
    }
}
