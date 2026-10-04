package com.tb.producer.application.port.in;

import com.tb.producer.domain.model.Pedido;

/**
 * Inbound Port — Caso de uso: Criar Pedido.
 * Chamado pelos adaptadores de entrada (ex: REST Controller).
 */
public interface CriarPedidoUseCase {

    /**
     * Cria um novo pedido e dispara o evento de envio.
     *
     * @param command dados de entrada validados
     * @return o pedido criado
     */
    Pedido criarPedido(CriarPedidoCommand command);
}
