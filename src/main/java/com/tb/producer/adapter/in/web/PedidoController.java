package com.tb.producer.adapter.in.web;

import com.tb.producer.application.port.in.CriarPedidoCommand;
import com.tb.producer.application.port.in.CriarPedidoUseCase;
import com.tb.producer.domain.model.Pedido;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Adaptador Inbound — REST Controller.
 * Recebe requisições HTTP e as converte para chamadas ao caso de uso.
 */
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;

    public PedidoController(CriarPedidoUseCase criarPedidoUseCase) {
        this.criarPedidoUseCase = criarPedidoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(@Valid @RequestBody CriarPedidoRequest request) {
        // Mapeia DTO REST → Command (ambos são adaptadores externos)
        CriarPedidoCommand command = request.toCommand();
        Pedido pedido = criarPedidoUseCase.criarPedido(command);
        return PedidoResponse.from(pedido);
    }
}
