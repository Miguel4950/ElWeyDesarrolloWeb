package com.elwey.restaurante.controller;

import com.elwey.restaurante.entities.Pedido;
import com.elwey.restaurante.service.ClienteService;
import com.elwey.restaurante.service.DomiciliarioService;
import com.elwey.restaurante.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private DomiciliarioService domiciliarioService;

    Logger log = LoggerFactory.getLogger(PedidoController.class);

    // http://localhost:8080/pedidos
    @GetMapping()
    public String mostrarPedidos(Model model) {
        log.info("Consultando lista completa de pedidos");
        model.addAttribute("pedidos", pedidoService.searchAll());
        return "pedidos";
    }

    // http://localhost:8080/pedidos/1
    @GetMapping("/{id}")
    public String mostrarPedidoPorId(Model model, @PathVariable("id") Long id) {
        log.info("Consultando detalle de pedido con ID: {}", id);
        Pedido pedido = pedidoService.searchById(id);
        model.addAttribute("pedido", pedido);
        return "pedido-detalle";
    }

    // http://localhost:8080/pedidos/delete/1
    @GetMapping("/delete/{id}")
    public String eliminarPedido(@PathVariable("id") Long id) {
        log.info("Eliminando pedido con ID: {}", id);
        pedidoService.delete(id);
        return "redirect:/pedidos?eliminado";
    }

    // POST http://localhost:8080/pedidos/ordenar
    @PostMapping("/ordenar")
    public String ordenarPlatoConAdicionales(
            @RequestParam("comidaId") Long comidaId,
            @RequestParam(name = "adicionalesIds", required = false) List<Long> adicionalesIds) {
        log.info("Creando pedido para comida ID {} con adicionales seleccionados: {}", comidaId, adicionalesIds);
        pedidoService.crearPedidoRapido(comidaId, adicionalesIds);
        return "redirect:/pedidos";
    }
}
