package com.rayllanderson.ecommerce.producers;

import com.rayllanderson.ecommerce.utils.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerService<T> implements Closeable {

    private final KafkaProducer<String, T> producer;
    private final static Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    public KafkaProducerService() {
        this.producer = new KafkaProducer<>(properties());
    }


    private static Properties properties() {
        Properties properties = new Properties();

        //onde vamos nos conectar
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        //Vamos utilizar os valores em String
        //por isso estamos setando um serializador aqui
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

        return properties;
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
        Callback callback = (data, exception) -> {
            if (exception != null) {
                logger.error("Ocorreu um erro. Causa {}", exception.getMessage());
                return;
            }
            logger.info("Sucesso! Tópico envido {}:::/{}/{}/{}", data.topic(), data.partition(), data.offset(), data.timestamp());
        };
        this.producer.send(new ProducerRecord<>(topic, key, value), callback).get();
    }

    @Override
    public void close() {
        producer.close();
    }
}
