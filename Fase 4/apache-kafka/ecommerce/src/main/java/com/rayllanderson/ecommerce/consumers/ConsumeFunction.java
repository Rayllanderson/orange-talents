package com.rayllanderson.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumeFunction {
    void consume(ConsumerRecord<String, String> record);
}
