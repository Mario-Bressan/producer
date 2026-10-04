package com.tb.producer.domain.model;

import java.util.Objects;

/**
 * Value Object — Identificador do Cliente.
 */
public record ClienteId(String valor) {

    public ClienteId {
        Objects.requireNonNull(valor, "valor não pode ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException("clienteId não pode ser vazio");
        }
    }

    @Override
    public String toString() {
        return valor;
    }
}
