package com.elwey.restaurante.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elwey.restaurante.entities.Pedido;
import com.elwey.restaurante.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    Logger log = LoggerFactory.getLogger(PedidoController.class);

    // http://localhost:8080/pedidos/all
    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los pedidos del restaurante")
    public ResponseEntity<List<Pedido>> mostrarPedidos() {
        // 1. Consultar lista completa de pedidos
        List<Pedido> pedidos = pedidoService.searchAll();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // http://localhost:8080/pedidos/1 o http://localhost:8080/pedidos/find/1
    @GetMapping({"/{id}", "/find/{id}"})
    @Operation(summary = "Obtiene el detalle de un pedido por su ID")
    public ResponseEntity<Pedido> mostrarPedidoPorId(@PathVariable("id") Long id) {
        // 1. Buscar pedido por identificador
        Pedido pedido = pedidoService.searchById(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    // http://localhost:8080/pedidos/cliente/1
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtiene todos los pedidos asociados a un cliente")
    public ResponseEntity<List<Pedido>> mostrarPedidosPorCliente(@PathVariable("clienteId") Long clienteId) {
        // 1. Filtrar pedidos pertenecientes al cliente indicado
        List<Pedido> pedidos = pedidoService.findByCliente(clienteId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // http://localhost:8080/pedidos/ordenar
    @PostMapping("/ordenar")
    @Operation(summary = "Crea un nuevo pedido con un plato, cliente, cantidad y adicionales")
    public ResponseEntity<Pedido> ordenarPlato(
            @RequestParam("comidaId") Long comidaId,
            @RequestParam(name = "clienteId", required = false) Long clienteId,
            @RequestParam(name = "cantidad", required = false, defaultValue = "1") Integer cantidad,
            @RequestParam(name = "adicionalesIds", required = false) List<Long> adicionalesIds) {
        // 1. Crear pedido calculando subtotales y asignando domiciliario disponible
        log.info("Creando pedido para comida ID {} y cliente ID {}", comidaId, clienteId);
        Pedido nuevoPedido = pedidoService.crearPedidoOrden(comidaId, clienteId, adicionalesIds, cantidad);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    // http://localhost:8080/pedidos/1/estado
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualiza el estado de un pedido (RECIBIDO, COCINANDO, ENVIADO, ENTREGADO, CANCELADO)")
    public ResponseEntity<Pedido> cambiarEstadoPedido(
            @PathVariable("id") Long id,
            @RequestBody Pedido body) {
        // 1. Obtener el nuevo estado y actualizar el pedido
        String estado = body.getEstado();
        Pedido actualizado = pedidoService.cambiarEstado(id, estado);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/pedidos/1/domiciliario/2
    @PutMapping("/{id}/domiciliario/{domiciliarioId}")
    @Operation(summary = "Asigna o cambia el domiciliario encargado de un pedido")
    public ResponseEntity<Pedido> asignarDomiciliario(
            @PathVariable("id") Long id,
            @PathVariable("domiciliarioId") Long domiciliarioId) {
        // 1. Vincular el domiciliario seleccionado al pedido
        Pedido actualizado = pedidoService.asignarDomiciliario(id, domiciliarioId);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/pedidos/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un pedido por su ID")
    public ResponseEntity<Void> eliminarPedido(@PathVariable("id") Long id) {
        // 1. Eliminar pedido y sus ítems en cascada
        log.info("Eliminando pedido con ID: {}", id);
        pedidoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
