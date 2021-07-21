package com.rayllanderson.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(LogConsumerService.class);

    public static void main(String[] args) {
        String groupId = LogConsumerService.class.getSimpleName();
        new KafkaConsumerService(groupId, "ECOMMERCE.*", LogConsumerService::parse).run();
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
