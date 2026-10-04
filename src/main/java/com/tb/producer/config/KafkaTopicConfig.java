package com.tb.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuração programática de tópicos Kafka.
 */
@Configuration
public class KafkaTopicConfig {

    public static final String TOPIC_PEDIDOS = "tb-pedidos-v1";

    @Bean
    public NewTopic pedidosTopic() {
        return TopicBuilder.name(TOPIC_PEDIDOS)
            .partitions(3)
            .replicas(1)
            .build();
    }
}
