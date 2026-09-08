package com.elwey.restaurante.controller;

import com.elwey.restaurante.entities.Cliente;
import com.elwey.restaurante.service.ClienteService;
import com.elwey.restaurante.service.ComidaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ComidaService service;

    @Autowired
    private ClienteService clienteService;

    /**
     * GET /, GET /index, GET /home – Muestra la página principal con la lista de
     * comidas.
     * Envía al modelo:
     * • comidas – colección de platos para mostrar en la portada
     */
    @GetMapping({ "/", "/index", "/home" })
    public String inicio(Model model) {
        model.addAttribute("comidas", service.listarTodas());
        return "index";
    }

    /**
     * GET /menu – Redirige a la vista de tarjetas del menú.
     * Envía al modelo:
     * • no agrega datos, solo redirige a la ruta definida
     */
    @GetMapping("/menu")
    public String menuRedirect() {
        return "redirect:/comidas/tarjetas";
    }

    /**
     * GET /login – Muestra la pantalla de inicio de sesión del cliente.
     * Envía al modelo:
     * • no agrega datos, solo carga la vista de login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * POST /login – Valida las credenciales del usuario e inicia la sesión.
     * Recibe:
     * • email – correo del cliente
     * • password – contraseña ingresada
     * • rol – perfil seleccionado por el usuario
     * Envía al modelo:
     * • errorMessage – mensaje de credenciales inválidas obtenido de la excepción
     * • error – bandera para mostrar error en la vista
     * • email – valor reescrito para mantener el formulario
     * • selectedRol – rol elegido por el usuario
     */
    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "CLIENTE") String rol,
            Model model) {
        try {
            Cliente cliente = clienteService.autenticar(email, password);
            return "redirect:/cliente/portal/" + cliente.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("email", email);
            model.addAttribute("selectedRol", rol);
            return "login";
        }
    }

    /**
     * GET /registro – Muestra el formulario para crear una nueva cuenta.
     * Envía al modelo:
     * • cliente – objeto Cliente vacío para el formulario
     */
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "registro";
    }

    /**
     * POST /registro – Registra un nuevo cliente en la aplicación.
     * Recibe:
     * • cliente – objeto Cliente con los datos enviados por el formulario
     * • rol – perfil seleccionado al registrar
     * • model – modelo para enviar atributos a la vista
     * En caso de error capturado por la excepción del servicio:
     * • errorMessage – mensaje si el correo ya existe o no es válido
     * • error – bandera para mostrar el error
     * • selectedRol – rol seleccionado para conservarlo en el formulario
     */
    @PostMapping("/registro")
    public String registrarCliente(
            @ModelAttribute("cliente") Cliente cliente,
            @RequestParam(required = false, defaultValue = "CLIENTE") String rol,
            Model model) {
        try {
            clienteService.guardar(cliente);
            return "redirect:/login?exito";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("selectedRol", rol);
            return "registro";
        }
    }

    /**
     * GET /admin/productos – Muestra el listado administrativo de platos con
     * búsqueda opcional.
     * Recibe:
     * • q – texto de búsqueda por nombre del plato
     * Envía al modelo:
     * • comidas – resultados filtrados por nombre
     * • query – término de búsqueda ingresado
     * • esAdmin – bandera que activa el modo administración
     */
    @GetMapping("/admin/productos")
    public String adminProductos(@RequestParam(name = "q", required = false) String query, Model model) {
        model.addAttribute("comidas", service.buscarPorNombre(query));
        model.addAttribute("query", query);
        // Dependiendo de la URL (/admin/productos) enviamos true al modelo
        model.addAttribute("esAdmin", true);
        return "comidas-tabla";
    }

    /**
     * GET /admin/vertodo – Muestra la vista administrativa con todas las tarjetas
     * de clientes.
     * Envía al modelo:
     * • clientes – listado completo de clientes registrados
     */
    @GetMapping("/admin/vertodo")
    public String verTodosLosClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "admin-clientes";
    }
}