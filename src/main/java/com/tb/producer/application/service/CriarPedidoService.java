package com.tb.producer.application.service;

import com.tb.producer.application.port.in.CriarPedidoCommand;
import com.tb.producer.application.port.in.CriarPedidoUseCase;
import com.tb.producer.application.port.out.EnviarPedidoEventPort;
import com.tb.producer.domain.model.ClienteId;
import com.tb.producer.domain.model.ItemPedido;
import com.tb.producer.domain.model.Pedido;

import java.util.List;

/**
 * Implementação do caso de uso CriarPedido.
 * Orquestra a criação da entidade de domínio e o disparo do evento de saída.
 */
public class CriarPedidoService implements CriarPedidoUseCase {

    private final EnviarPedidoEventPort enviarPedidoEventPort;

    public CriarPedidoService(EnviarPedidoEventPort enviarPedidoEventPort) {
        this.enviarPedidoEventPort = enviarPedidoEventPort;
    }

    @Override
    public Pedido criarPedido(CriarPedidoCommand command) {
        // 1. Mapear command → domínio
        ClienteId clienteId = new ClienteId(command.clienteId());
        List<ItemPedido> itens = command.itens().stream()
            .map(item -> new ItemPedido(item.produto(), item.quantidade(), item.precoUnitario()))
            .toList();

        // 2. Criar entidade de domínio (regras de negócio internas)
        Pedido pedido = Pedido.criar(clienteId, itens);

        // 3. Disparar evento de saída pela porta
        enviarPedidoEventPort.enviar(pedido);

        return pedido;
    }
}
