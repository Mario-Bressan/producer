package com.tb.producer.adapter.in.web;

import com.tb.producer.application.port.in.CriarPedidoCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO REST de requisição — contrato externo da API.
 * Totalmente separado do domínio e do command.
 */
public record CriarPedidoRequest(
    @NotBlank(message = "clienteId é obrigatório")
    String clienteId,

    @NotEmpty(message = "itens não pode ser vazio")
    List<@Valid ItemRequest> itens
) {

    public record ItemRequest(
        @NotBlank(message = "produto é obrigatório")
        String produto,

        @Positive(message = "quantidade deve ser maior que zero")
        int quantidade,

        @NotNull(message = "precoUnitario é obrigatório")
        @Positive(message = "precoUnitario deve ser maior que zero")
        BigDecimal precoUnitario
    ) {}

    CriarPedidoCommand toCommand() {
        return new CriarPedidoCommand(
            clienteId,
            itens.stream()
                .map(i -> new CriarPedidoCommand.ItemCommand(i.produto, i.quantidade, i.precoUnitario))
                .toList()
        );
    }
}
