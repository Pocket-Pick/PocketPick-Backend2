package com.flab.pocketpick.user.infra.grpc;

import com.flab.pocketpick.user.global.exception.GrpcClientException;
import com.flab.pocketpick.user.grpc.AuthServiceGrpc;
import com.flab.pocketpick.user.grpc.CreateCredentialRequest;
import com.flab.pocketpick.user.grpc.DeleteCredentialRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthGrpcClient {

    private final AuthServiceGrpc.AuthServiceBlockingStub stub;

    public AuthGrpcClient(GrpcChannelFactory channelFactory) {
        ManagedChannel channel = channelFactory.createChannel("auth");
        this.stub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @CircuitBreaker(name = "auth-grpc")
    public void createCredential(String email, String password) {
        try {
            stub.createCredential(CreateCredentialRequest.newBuilder()
                    .setEmail(email)
                    .setPassword(password)
                    .build());
        } catch (StatusRuntimeException e) {
            throw new GrpcClientException(e.getStatus().getCode(), e.getStatus().getDescription());
        }
    }

    public void deleteCredential(String email) {
        try {
            stub.deleteCredential(DeleteCredentialRequest.newBuilder()
                    .setEmail(email)
                    .build());
        } catch (Exception e) {
            log.error("Auth 보상 호출 실패 - email: {}", email, e);
        }
    }
}
