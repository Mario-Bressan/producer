package com.tb.producer.adapter.out.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tb.producer.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO de evento Kafka — contrato externo da mensageria.
 * Totalmente separado do domínio.
 */
record PedidoEventPayload(
        UUID id,
        String clienteId,
        List<ItemPayload> itens,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
        Instant dataCriacao,

        String status,
        BigDecimal valorTotal
) {

    record ItemPayload(
            String produto,
            int quantidade,
            BigDecimal precoUnitario,
            BigDecimal subtotal
    ) {
    }

    static PedidoEventPayload from(Pedido pedido) {
        return new PedidoEventPayload(
                pedido.id().valor(),
                pedido.clienteId().valor(),
                pedido.itens().stream()
                        .map(i -> new ItemPayload(i.produto(), i.quantidade(), i.precoUnitario(), i.subtotal()))
                        .toList(),
                pedido.dataCriacao(),
                pedido.status().name(),
                pedido.valorTotal()
        );
    }
}