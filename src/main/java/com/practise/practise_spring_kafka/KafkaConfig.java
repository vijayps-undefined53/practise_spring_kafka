package com.practise.practise_spring_kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, SampleObject> producerFactory() {
        Map<String, Object> configProps = new HashMap<>(kafkaProperties.buildProducerProperties());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, SampleObject> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, SampleObject> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>(kafkaProperties.buildConsumerProperties());
        return new DefaultKafkaConsumerFactory(configProps, new StringDeserializer(), new JsonDeserializer(SampleObject.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SampleObject>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SampleObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}