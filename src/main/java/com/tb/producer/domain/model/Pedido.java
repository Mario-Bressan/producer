package com.tb.producer.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio pura — sem anotações de framework, Lombok ou Jackson.
 * Representa um pedido no sistema.
 */
public class Pedido {

    private final PedidoId id;
    private final ClienteId clienteId;
    private final List<ItemPedido> itens;
    private final Instant dataCriacao;
    private StatusPedido status;
    private BigDecimal valorTotal;

    private Pedido(PedidoId id, ClienteId clienteId, List<ItemPedido> itens) {
        this.id = Objects.requireNonNull(id, "id não pode ser nulo");
        this.clienteId = Objects.requireNonNull(clienteId, "clienteId não pode ser nulo");
        this.itens = new ArrayList<>(Objects.requireNonNull(itens, "itens não pode ser nulo"));
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve ter ao menos um item");
        }
        this.dataCriacao = Instant.now();
        this.status = StatusPedido.CRIADO;
        this.valorTotal = calcularValorTotal();
    }

    public static Pedido criar(ClienteId clienteId, List<ItemPedido> itens) {
        return new Pedido(new PedidoId(UUID.randomUUID()), clienteId, itens);
    }

    public void marcarComoEnviado() {
        if (this.status != StatusPedido.CRIADO) {
            throw new IllegalStateException(
                "Somente pedidos com status CRIADO podem ser enviados. Status atual: " + this.status);
        }
        this.status = StatusPedido.ENVIADO;
    }

    private BigDecimal calcularValorTotal() {
        return itens.stream()
            .map(ItemPedido::subtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // --- Getters (sem setters públicos para imutabilidade externa) ---

    public PedidoId id() {
        return id;
    }

    public ClienteId clienteId() {
        return clienteId;
    }

    public List<ItemPedido> itens() {
        return Collections.unmodifiableList(itens);
    }

    public Instant dataCriacao() {
        return dataCriacao;
    }

    public StatusPedido status() {
        return status;
    }

    public BigDecimal valorTotal() {
        return valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido pedido)) return false;
        return id.equals(pedido.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + id +
            ", clienteId=" + clienteId +
            ", status=" + status +
            ", valorTotal=" + valorTotal +
            ", itens=" + itens.size() +
            '}';
    }
}
