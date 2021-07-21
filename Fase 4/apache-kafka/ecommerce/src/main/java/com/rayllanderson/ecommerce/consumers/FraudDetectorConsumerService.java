package com.rayllanderson.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FraudDetectorConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(FraudDetectorConsumerService.class);

    public static void main(String[] args) {
        String groupId = FraudDetectorConsumerService.class.getSimpleName();
        try(var consumer = new KafkaConsumerService(groupId, "ECOMMERCE_NEW_ORDER", FraudDetectorConsumerService::parse)) {
            consumer.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record){
        logger.info("-------------------------------------------");
        logger.info("Processing new order. Checking for fraud");
        logger.info("Chave {}", record.key());
        logger.info("Valor {}", record.value());
        logger.info("Partição {}", record.partition());
        logger.info("Offset {}", record.offset());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Order processed");
    }
}
