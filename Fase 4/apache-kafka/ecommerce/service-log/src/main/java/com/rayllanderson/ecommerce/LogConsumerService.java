package com.rayllanderson.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Pattern;

public class LogConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(LogConsumerService.class);

    public static void main(String[] args) {
        String groupId = LogConsumerService.class.getSimpleName();
        new KafkaConsumerService<>(
                groupId, Pattern.compile("ECOMMERCE.*"), LogConsumerService::parse, String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())).run();
    }

    private static void parse(ConsumerRecord<String, String> record) {
        logger.info("-------------------------------------------");
        logger.info("LOG");
        logger.info("Tópico {}", record.topic());
        logger.info("Chave {}", record.key());
        logger.info("Valor {}", record.value());
        logger.info("Partição {}", record.partition());
        logger.info("Offset {}", record.offset());
    }
}
