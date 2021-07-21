package com.rayllanderson.ecommerce.consumers;

import com.rayllanderson.ecommerce.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FraudDetectorConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(FraudDetectorConsumerService.class);

    public static void main(String[] args) {
        var fraudService = new FraudDetectorConsumerService();
        String groupId = FraudDetectorConsumerService.class.getSimpleName();
        try(var consumer = new KafkaConsumerService<>(groupId,
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                Map.of())){
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record){
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
