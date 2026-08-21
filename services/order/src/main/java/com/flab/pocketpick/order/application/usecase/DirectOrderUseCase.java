package com.flab.pocketpick.order.application.usecase;

import com.flab.pocketpick.order.application.dto.CreateDirectOrderRequest;

public interface DirectOrderUseCase {

    void createDirectOrder(Long customerId, CreateDirectOrderRequest request);
}
