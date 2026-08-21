package com.flab.pocketpick.order.infra.outbox;

import org.apache.kafka.common.errors.SerializationException;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AvroSerializer {

    @FunctionalInterface
    public interface AvroEncoder<T> {
        ByteBuffer encode(T value) throws IOException;
    }

    public static <T> byte[] serialize(T event, AvroEncoder<T> encoder) {
        try {
            return encoder.encode(event).array();
        } catch (IOException e) {
            throw new SerializationException("Avro 직렬화 실패: " + event.getClass().getSimpleName(), e);
        }
    }
}
