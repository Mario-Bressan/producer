package com.tb.producer.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object — Identificador do Pedido.
 */
public record PedidoId(UUID valor) {

    public PedidoId {
        Objects.requireNonNull(valor, "valor não pode ser nulo");
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
