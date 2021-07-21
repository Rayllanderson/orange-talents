package com.rayllanderson.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    private final static Logger logger = LoggerFactory.getLogger(NewOrderMain.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //criando o produtor
        KafkaProducer <String, String> producer = new KafkaProducer<>(properties());

        //criando a mensagem
        String key = "id,userId,price";
        String value = "123131,121,150.3";
        ProducerRecord<String, String> record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);

        var email = "Thank you for your order! We are processing your order!";
        ProducerRecord<String, String> emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", email, email);

        //enviando a mensagem. O get faz a gente esperar até que ela seja enviada, daí fazer isso.
        //um callback, igual then(data => {}) do JS..
        Callback callback = (data, exception) -> {
            if (exception != null) {
                logger.error("Ocorreu um erro. Causa {}", exception.getMessage());
                return;
            }
            logger.info("Sucesso! Tópico envido {}:::/{}/{}/{}", data.topic(), data.partition(), data.offset(), data.timestamp());
        };
        producer.send(record, callback).get();
        producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {
        Properties properties = new Properties();

        //onde vamos nos conectar
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        //Vamos utilizar os valores em String
        //por isso estamos setando um serializador aqui
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
