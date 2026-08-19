package com.flab.pocketpick.user.infra.grpc;

import com.flab.pocketpick.user.global.exception.GrpcClientException;
import com.flab.pocketpick.user.grpc.AuthServiceGrpc;
import com.flab.pocketpick.user.grpc.CreateCredentialRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

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
}
