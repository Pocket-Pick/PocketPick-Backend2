package com.flab.pocketpick.order.infra.grpc;

import com.flab.pocketpick.order.global.exception.GrpcClientException;
import com.flab.pocketpick.order.grpc.GetPostPriceRequest;
import com.flab.pocketpick.order.grpc.PostServiceGrpc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PostGrpcClient {

    private final PostServiceGrpc.PostServiceBlockingStub stub;

    public PostGrpcClient(GrpcChannelFactory channelFactory) {
        ManagedChannel channel = channelFactory.createChannel("post-service");
        this.stub = PostServiceGrpc.newBlockingStub(channel);
    }

    @CircuitBreaker(name = "post-grpc")
    public long getPostPrice(Long postId) {
        GetPostPriceRequest request = GetPostPriceRequest.newBuilder()
                .setPostId(postId)
                .build();
        try {
            return stub.withDeadlineAfter(1000, TimeUnit.MILLISECONDS)
                    .getPostPrice(request)
                    .getPrice();
        } catch (StatusRuntimeException e) {
            throw new GrpcClientException(e.getStatus().getCode(), e.getStatus().getDescription());
        }
    }
}
