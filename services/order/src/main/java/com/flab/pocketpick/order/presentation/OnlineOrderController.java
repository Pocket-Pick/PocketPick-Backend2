package com.flab.pocketpick.order.presentation;

import com.flab.pocketpick.order.application.dto.CreateOnlineOrderRequest;
import com.flab.pocketpick.order.application.usecase.OnlineOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/online")
@RequiredArgsConstructor
public class OnlineOrderController {

    private final OnlineOrderUseCase onlineOrderUseCase;

    @PostMapping
    public ResponseEntity<Void> createOnlineOrder(
            @AuthenticationPrincipal Long customerId,
            @Valid @RequestBody CreateOnlineOrderRequest request
    ) {
        onlineOrderUseCase.createOnlineOrder(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
