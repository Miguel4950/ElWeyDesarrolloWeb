package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Cliente;
import com.elwey.restaurante.entities.Pedido;
import com.elwey.restaurante.errors.ClienteNotFoundException;
import com.elwey.restaurante.repository.ClienteRepository;
import com.elwey.restaurante.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        log.info("Listando todos los clientes desde el servicio");
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        log.info("Buscando cliente con ID: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerPorEmail(String email) {
        log.info("Buscando cliente con email: " + email);
        if (email == null) {
            return null;
        }
        return repo.findByEmail(email.trim()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente autenticar(String email, String password) {
        log.info("Autenticando cliente con email: " + email);
        Cliente cliente = obtenerPorEmail(email);
        if (cliente == null || password == null || !password.equals(cliente.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas. Verifica tu email y contraseña.");
        }
        return cliente;
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        log.info("Guardando o actualizando cliente: " + (cliente != null ? cliente.getNombre() : "null"));
        if (cliente == null || cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new IllegalArgumentException("El correo ya existe o no es válido.");
        }
        Cliente existente = obtenerPorEmail(cliente.getEmail());
        if (existente != null && (cliente.getId() == null || !existente.getId().equals(cliente.getId()))) {
            throw new IllegalArgumentException("El correo ya existe o no es válido.");
        }

        if (cliente.getId() != null) {
            repo.findById(cliente.getId()).ifPresent(actual -> {
                if (cliente.getPassword() == null || cliente.getPassword().isBlank()) {
                    cliente.setPassword(actual.getPassword());
                }
                if (cliente.getFechaRegistro() == null || cliente.getFechaRegistro().isBlank()) {
                    cliente.setFechaRegistro(actual.getFechaRegistro());
                }
            });
        }

        if (cliente.getFechaRegistro() == null || cliente.getFechaRegistro().isBlank()) {
            cliente.setFechaRegistro(java.time.LocalDate.now().toString());
        }
        if (cliente.getActivo() == null) {
            cliente.setActivo(true);
        }
        return repo.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        if (!repo.existsById(id)) {
            throw new ClienteNotFoundException(id);
        }
        List<Pedido> pedidosCliente = pedidoRepository.findByClienteId(id);
        for (Pedido p : pedidosCliente) {
            pedidoService.delete(p.getId());
        }
        repo.deleteById(id);
    }
}