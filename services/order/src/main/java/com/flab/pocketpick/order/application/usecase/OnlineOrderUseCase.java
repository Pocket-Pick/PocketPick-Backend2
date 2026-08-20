package com.flab.pocketpick.order.application.usecase;

import com.flab.pocketpick.order.application.dto.CreateOnlineOrderRequest;

public interface OnlineOrderUseCase {

    void createOnlineOrder(Long customerId, CreateOnlineOrderRequest request);
}
