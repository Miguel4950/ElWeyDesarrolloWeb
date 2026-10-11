package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.ItemPedido;
import com.elwey.restaurante.entities.Pedido;
import java.util.List;

public interface PedidoService {
    List<Pedido> searchAll();
    Pedido searchById(Long id);
    List<Pedido> findByCliente(Long clienteId);
    void save(Pedido pedido);
    void delete(Long id);
    void crearPedidoRapido(Long comidaId, List<Long> adicionalesIds);
    Pedido crearPedidoOrden(Long comidaId, Long clienteId, List<Long> adicionalesIds, Integer cantidad);
    Pedido cambiarEstado(Long pedidoId, String nuevoEstado);
    Pedido asignarDomiciliario(Long pedidoId, Long domiciliarioId);

    Double calcularSubtotal(ItemPedido item);
    Double calcularTotal(Pedido pedido);
    void calcularTotales(Pedido pedido);
}
