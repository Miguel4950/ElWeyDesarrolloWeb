package com.elwey.restaurante.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.elwey.restaurante.entities.Cliente;
import com.elwey.restaurante.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // http://localhost:8080/clientes/all
    @GetMapping("/all")
    @Operation(summary = "Encuentra todos los clientes registrados")
    public ResponseEntity<List<Cliente>> mostrarClientes() {
        // 1. Obtener listado completo de clientes
        List<Cliente> clientes = clienteService.listarTodos();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // http://localhost:8080/clientes/1
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un cliente por su ID")
    public ResponseEntity<Cliente> mostrarClientePorId(@PathVariable("id") Long id) {
        // 1. Buscar cliente por identificador
        Cliente cliente = clienteService.obtenerPorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // http://localhost:8080/clientes/login
    @PostMapping("/login")
    @Operation(summary = "Autentica a un cliente mediante correo y contraseña")
    public ResponseEntity<Cliente> iniciarSesion(@RequestBody Map<String, String> credenciales) {
        // 1. Extraer email/correo y password/contrasena del cuerpo de la petición
        String email = credenciales.getOrDefault("email", credenciales.get("correo"));
        String password = credenciales.getOrDefault("password", credenciales.get("contrasena"));
        // 2. Autenticar credenciales a través del servicio de negocio
        Cliente cliente = clienteService.autenticar(email, password);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // http://localhost:8080/clientes/add
    @PostMapping("/add")
    @Operation(summary = "Registra un nuevo cliente en el sistema")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        // 1. Forzar ID nulo para garantizar la creación de un nuevo registro
        cliente.setId(null);
        log.info("Registrando nuevo cliente con correo: {}", cliente.getEmail());
        Cliente creado = clienteService.guardar(cliente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // http://localhost:8080/clientes/update
    @PutMapping("/update")
    @Operation(summary = "Actualiza los datos de un cliente existente")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente) {
        // 1. Verificar que el cliente tenga ID válido antes de actualizar
        if (cliente.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Actualizando datos del cliente ID: {}", cliente.getId());
        Cliente actualizado = clienteService.guardar(cliente);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // http://localhost:8080/clientes/delete/1
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Elimina un cliente por su ID")
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Long id) {
        // 1. Eliminar cuenta del cliente y sus pedidos asociados
        log.info("Eliminando cuenta del cliente ID: {}", id);
        clienteService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
