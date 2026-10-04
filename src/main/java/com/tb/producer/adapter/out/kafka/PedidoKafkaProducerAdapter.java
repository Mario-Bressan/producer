package com.tb.producer.adapter.out.kafka;

import com.tb.producer.application.port.out.EnviarPedidoEventPort;
import com.tb.producer.domain.model.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

/**
 * Adaptador Outbound — Kafka Producer.
 * Implementa EnviarPedidoEventPort usando KafkaTemplate.
 */
public class PedidoKafkaProducerAdapter implements EnviarPedidoEventPort {

    private static final Logger log = LoggerFactory.getLogger(PedidoKafkaProducerAdapter.class);
    private static final String TOPIC = "tb-pedidos-v1";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PedidoKafkaProducerAdapter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviar(Pedido pedido) {
        // Mapeia entidade de domínio → DTO de evento Kafka
        PedidoEventPayload payload = PedidoEventPayload.from(pedido);

        // Usa clienteId como chave para particionamento consistente
        String key = pedido.clienteId().valor();

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, key, payload);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Pedido enviado com sucesso: id={}, partition={}, offset={}",
                    pedido.id(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            } else {
                log.error("Falha ao enviar pedido: id={}", pedido.id(), ex);
            }
        });
    }
}
