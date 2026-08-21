package com.flab.pocketpick.order.infra.kafka;

import com.flab.pocketpick.order.avro.CancelOrderCommand;
import com.flab.pocketpick.order.avro.ConfirmOrderCommand;
import com.flab.pocketpick.order.domain.order.OnlineOrder;
import com.flab.pocketpick.order.domain.order.exception.OrderNotFoundException;
import com.flab.pocketpick.order.infra.kafka.aop.IdempotentConsumer;
import com.flab.pocketpick.order.infra.persistence.OnlineOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RetryableTopic(
        attempts = "4",
        backOff = @BackOff(delay = 1000, multiplier = 2.0, maxDelay = 10000, jitter = 300),
        dltStrategy = DltStrategy.FAIL_ON_ERROR,
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
        kafkaTemplate = "retryKafkaTemplate"
)
@KafkaListener(topics = "order.commands", containerFactory = "avroKafkaListenerContainerFactory")
public class OrderCommandConsumer {

    private final OnlineOrderRepository onlineOrderRepository;

    @KafkaHandler
    @IdempotentConsumer(key = "#command.sagaId + ':CONFIRM_ORDER'")
    public void handleConfirm(ConfirmOrderCommand command) {
        OnlineOrder order = onlineOrderRepository.findById(command.getOrderId())
                .orElseThrow(OrderNotFoundException::new);
        order.confirm();
    }

    @KafkaHandler
    @IdempotentConsumer(key = "#command.sagaId + ':CANCEL_ORDER'")
    public void handleCancel(CancelOrderCommand command) {
        OnlineOrder order = onlineOrderRepository.findById(command.getOrderId())
                .orElseThrow(OrderNotFoundException::new);
        order.cancel();
    }

    @KafkaHandler(isDefault = true)
    public void handleUnknown(GenericRecord record) {
        log.warn("[OrderCommandConsumer] 알 수 없는 커맨드: {}", record.getSchema().getName());
    }

    @DltHandler
    public void handleDlt(Object record) {
        log.error("[OrderCommandConsumer] DLT 수신 - 모든 재시도 소진: record={}", record);
    }
}
