package com.rayllanderson.ecommerce.producers;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var producer = new KafkaProducerService()) {
            for (var i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();

                var orderValue = key + ",121,150.3";
                producer.send("ECOMMERCE_NEW_ORDER", key, orderValue);

                var email = "Thank you for your order! We are processing your order!";
                producer.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }
}
