package com.flab.pocketpick.order.presentation;

import com.flab.pocketpick.order.application.dto.CreateDirectOrderRequest;
import com.flab.pocketpick.order.application.usecase.DirectOrderUseCase;
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
@RequestMapping("/orders/direct")
@RequiredArgsConstructor
public class DirectOrderController {

    private final DirectOrderUseCase directOrderUseCase;

    @PostMapping
    public ResponseEntity<Void> createDirectOrder(
            @AuthenticationPrincipal Long customerId,
            @Valid @RequestBody CreateDirectOrderRequest request
    ) {
        directOrderUseCase.createDirectOrder(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
