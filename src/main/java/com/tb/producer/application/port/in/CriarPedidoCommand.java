package com.tb.producer.application.port.in;

import com.tb.producer.domain.model.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command (DTO de entrada) para o caso de uso CriarPedido.
 * Validações via Jakarta Bean Validation — nenhuma dependência do Spring.
 */
public record CriarPedidoCommand(
    @NotBlank(message = "clienteId é obrigatório")
    String clienteId,

    @NotEmpty(message = "itens não pode ser vazio")
    List<@Valid ItemCommand> itens
) {

    public record ItemCommand(
        @NotBlank(message = "produto é obrigatório")
        String produto,

        @Positive(message = "quantidade deve ser maior que zero")
        int quantidade,

        @NotNull(message = "precoUnitario é obrigatório")
        @Positive(message = "precoUnitario deve ser maior que zero")
        BigDecimal precoUnitario
    ) {}
}
