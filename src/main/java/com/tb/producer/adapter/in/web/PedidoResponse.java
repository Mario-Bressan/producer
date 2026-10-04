package com.tb.producer.adapter.in.web;

import com.tb.producer.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO REST de resposta — contrato externo da API.
 */
public record PedidoResponse(
    UUID id,
    String clienteId,
    List<ItemResponse> itens,
    Instant dataCriacao,
    String status,
    BigDecimal valorTotal
) {

    public record ItemResponse(
        String produto,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
    ) {}

    static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(
            pedido.id().valor(),
            pedido.clienteId().valor(),
            pedido.itens().stream()
                .map(i -> new ItemResponse(i.produto(), i.quantidade(), i.precoUnitario(), i.subtotal()))
                .toList(),
            pedido.dataCriacao(),
            pedido.status().name(),
            pedido.valorTotal()
        );
    }
}
