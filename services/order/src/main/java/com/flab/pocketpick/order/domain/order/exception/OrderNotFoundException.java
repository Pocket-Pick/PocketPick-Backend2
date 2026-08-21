package com.flab.pocketpick.order.domain.order.exception;

import com.flab.pocketpick.order.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException() {
        super("주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
