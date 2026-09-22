package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Adicional;
import com.elwey.restaurante.entities.Cliente;
import com.elwey.restaurante.entities.Comida;
import com.elwey.restaurante.entities.Domiciliario;
import com.elwey.restaurante.entities.ItemPedido;
import com.elwey.restaurante.entities.Pedido;
import com.elwey.restaurante.errors.ComidaNotFoundException;
import com.elwey.restaurante.errors.PedidoNotFoundException;
import com.elwey.restaurante.repository.AdicionalRepository;
import com.elwey.restaurante.repository.ClienteRepository;
import com.elwey.restaurante.repository.ComidaRepository;
import com.elwey.restaurante.repository.DomiciliarioRepository;
import com.elwey.restaurante.repository.ItemPedidoRepository;
import com.elwey.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private AdicionalRepository adicionalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DomiciliarioRepository domiciliarioRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public List<Pedido> searchAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido searchById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
    }

    @Override
    public List<Pedido> findByCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    @Transactional
    public void save(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pedido pedido = searchById(id);
        // Limpiar colecciones asociadas para garantizar integridad antes del borrado
        if (pedido.getItems() != null) {
            for (ItemPedido item : pedido.getItems()) {
                if (item.getAdicionales() != null) {
                    item.getAdicionales().clear();
                }
            }
            pedido.getItems().clear();
        }
        pedidoRepository.delete(pedido);
    }

    @Override
    @Transactional
    public void crearPedidoRapido(Long comidaId, List<Long> adicionalesIds) {
        Comida comida = comidaRepository.findById(comidaId)
                .orElseThrow(() -> new ComidaNotFoundException(comidaId));

        List<Cliente> clientes = clienteRepository.findAll();
        Cliente cliente = clientes.isEmpty() ? null : clientes.get(0);

        List<Domiciliario> domiciliarios = domiciliarioRepository.findAll();
        Domiciliario domiciliario = domiciliarios.isEmpty() ? null : domiciliarios.get(0);

        List<Adicional> seleccionados = new ArrayList<>();
        double totalAdicionales = 0.0;
        if (adicionalesIds != null && !adicionalesIds.isEmpty()) {
            for (Long adicId : adicionalesIds) {
                Adicional a = adicionalRepository.findById(adicId).orElse(null);
                if (a != null) {
                    seleccionados.add(a);
                    totalAdicionales += a.getPrecio();
                }
            }
        }

        double totalPedido = comida.getPrecio() + totalAdicionales;

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .domiciliario(domiciliario)
                .estado("En preparación")
                .fechaCreacion(LocalDateTime.now())
                .total(totalPedido)
                .build();
        pedido = pedidoRepository.save(pedido);

        ItemPedido item = ItemPedido.builder()
                .pedido(pedido)
                .comida(comida)
                .cantidad(1)
                .subtotal(totalPedido)
                .adicionales(seleccionados)
                .build();
        itemPedidoRepository.save(item);
    }
}
