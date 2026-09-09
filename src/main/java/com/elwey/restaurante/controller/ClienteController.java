package com.elwey.restaurante.controller;

import com.elwey.restaurante.entities.Cliente;
import com.elwey.restaurante.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    Logger log = LoggerFactory.getLogger(ClienteController.class);

    /**
     * GET /cliente/portal → Muestra el portal del cliente. Si se accede directo
     * desde la barra de navegación sin id, abre el perfil predeterminado (ID 1).
     */
    @GetMapping("/portal")
    public String miPortalDirecto(@RequestParam(name = "id", required = false, defaultValue = "1") Long id) {
        return "redirect:/cliente/portal/" + id;
    }

    /**
     * GET /cliente/portal/{id} → Muestra el portal del cliente con sus datos editables.
     */
    @GetMapping("/portal/{id}")
    public String miPortal(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);

        if (cliente == null) {
            log.warn("Cliente con ID {} no encontrado. Redirigiendo a login.", id);
            return "redirect:/login";
        }

        model.addAttribute("cliente", cliente);
        return "mi-portal";
    }

    /**
     * POST /cliente/portal/actualizar/{id} → Guarda los cambios del cliente y recarga el portal.
     */
    @PostMapping("/portal/actualizar/{id}")
    public String actualizarCliente(
            @ModelAttribute("cliente") Cliente cliente,
            @PathVariable("id") Long id) {
        cliente.setId(id);

        // Conservar contraseña y fecha de registro si vienen vacías
        Cliente actual = clienteService.obtenerPorId(id);
        if (actual != null) {
            if (cliente.getPassword() == null || cliente.getPassword().isBlank()) {
                cliente.setPassword(actual.getPassword());
            }
            if (cliente.getFechaRegistro() == null || cliente.getFechaRegistro().isBlank()) {
                cliente.setFechaRegistro(actual.getFechaRegistro());
            }
        }

        log.info("Actualizando datos del cliente ID: {}", id);
        clienteService.guardar(cliente);
        return "redirect:/cliente/portal/" + id;
    }

    /**
     * GET /cliente/eliminar/{id} → Elimina la cuenta del cliente y redirige al inicio.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        log.info("Eliminando cuenta del cliente ID: {}", id);
        clienteService.eliminar(id);
        return "redirect:/";
    }
}
