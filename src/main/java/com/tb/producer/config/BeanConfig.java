package com.tb.producer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tb.producer.adapter.out.kafka.PedidoKafkaProducerAdapter;
import com.tb.producer.application.port.in.CriarPedidoUseCase;
import com.tb.producer.application.port.out.EnviarPedidoEventPort;
import com.tb.producer.application.service.CriarPedidoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Configuração de beans da Arquitetura Hexagonal.
 * Define as implementações concretas para as portas.
 */
@Configuration
public class BeanConfig {

    @Bean
    public EnviarPedidoEventPort enviarPedidoEventPort(KafkaTemplate<String, Object> kafkaTemplate) {
        return new PedidoKafkaProducerAdapter(kafkaTemplate);
    }

    @Bean
    public CriarPedidoUseCase criarPedidoUseCase(EnviarPedidoEventPort enviarPedidoEventPort) {
        return new CriarPedidoService(enviarPedidoEventPort);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Registra o módulo para suportar Instant, LocalDateTime, etc.
        objectMapper.registerModule(new JavaTimeModule());
        // Desativa a gravação de datas como timestamps numéricos (escreve como ISO-8601)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
