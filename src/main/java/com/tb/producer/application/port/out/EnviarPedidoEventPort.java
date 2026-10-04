package com.tb.producer.application.port.out;

import com.tb.producer.domain.model.Pedido;

/**
 * Outbound Port — Enviar evento de pedido para o sistema de mensageria.
 * Implementada pelo adaptador Kafka.
 */
public interface EnviarPedidoEventPort {

    /**
     * Publica o pedido no tópico de mensageria.
     *
     * @param pedido o pedido do domínio a ser enviado
     */
    void enviar(Pedido pedido);
}
