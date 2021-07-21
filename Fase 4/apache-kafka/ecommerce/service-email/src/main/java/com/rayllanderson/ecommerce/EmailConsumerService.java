package com.rayllanderson.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EmailConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(EmailConsumerService.class);

    public static void main(String[] args) {
        String groupId = EmailConsumerService.class.getSimpleName();
        EmailConsumerService service = new EmailConsumerService();
        try(var consumer = new KafkaConsumerService<>(groupId,
                "ECOMMERCE_SEND_EMAIL",
                service::parse,
                Email.class,
                Map.of())) {
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record){
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
