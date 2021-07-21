package com.rayllanderson.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FraudDetectorConsumerService {

    private final static Logger logger = LoggerFactory.getLogger(FraudDetectorConsumerService.class);

    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        //Dizendo o tópico que estou escutando
        consumer.subscribe(Collections.singleton("ECOMMERCE_NEW_ORDER"));

        while(true) {
            //perguntando se tem registros em um tempo de 100 milis
            var records = consumer.poll(Duration.ofMillis(100));
            boolean isRecordsNotEmpty = !records.isEmpty();
            if (isRecordsNotEmpty) {
                logger.info("Encontrei {} registros", records.count());
                records.forEach(record -> {
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
                });
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorConsumerService.class.getSimpleName());
        return properties;
    }
}
