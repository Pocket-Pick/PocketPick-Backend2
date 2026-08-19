package com.flab.pocketpick.user.global.exception;

import io.grpc.Status;
import lombok.Getter;

@Getter
public class GrpcClientException extends RuntimeException {

    private final Status.Code code;

    public GrpcClientException(Status.Code code, String message) {
        super(message);
        this.code = code;
    }
}
