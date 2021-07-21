package com.rayllanderson.ecommerce.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(EmailConsumerService.class);

    public static void main(String[] args) {
        String groupId = EmailConsumerService.class.getSimpleName();
        try(var consumer = new KafkaConsumerService(groupId, "ECOMMERCE_SEND_EMAIL", EmailConsumerService::parse)) {
            consumer.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record){
        logger.info("-------------------------------------------");
        logger.info("Sending Email");
        logger.info("Chave {}", record.key());
        logger.info("Valor {}", record.value());
        logger.info("Partição {}", record.partition());
        logger.info("Offset {}", record.offset());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Email send");
    }
}
