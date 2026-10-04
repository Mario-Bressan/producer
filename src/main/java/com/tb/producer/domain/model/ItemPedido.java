package com.tb.producer.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object — Item de um pedido.
 */
public record ItemPedido(String produto, int quantidade, BigDecimal precoUnitario) {

    public ItemPedido {
        Objects.requireNonNull(produto, "produto não pode ser nulo");
        if (produto.isBlank()) {
            throw new IllegalArgumentException("produto não pode ser vazio");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser maior que zero");
        }
        Objects.requireNonNull(precoUnitario, "precoUnitario não pode ser nulo");
        if (precoUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("precoUnitario deve ser maior que zero");
        }
    }

    public BigDecimal subtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
