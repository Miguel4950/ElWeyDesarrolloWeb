package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Domiciliario;
import com.elwey.restaurante.entities.Pedido;
import com.elwey.restaurante.errors.DomiciliarioNotFoundException;
import com.elwey.restaurante.repository.DomiciliarioRepository;
import com.elwey.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DomiciliarioServiceImpl implements DomiciliarioService {

    @Autowired
    private DomiciliarioRepository domiciliarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Domiciliario> searchAll() {
        return domiciliarioRepository.findAll();
    }

    @Override
    public Domiciliario searchById(Long id) {
        return domiciliarioRepository.findById(id)
                .orElseThrow(() -> new DomiciliarioNotFoundException(id));
    }

    @Override
    @Transactional
    public void save(Domiciliario domiciliario) {
        guardar(domiciliario);
    }

    @Override
    @Transactional
    public Domiciliario guardar(Domiciliario domiciliario) {
        if (domiciliario.getDisponible() == null) {
            domiciliario.setDisponible(true);
        }
        return domiciliarioRepository.save(domiciliario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Domiciliario domiciliario = searchById(id);
        List<Pedido> pedidos = pedidoRepository.findAll();
        for (Pedido p : pedidos) {
            if (p.getDomiciliario() != null && id.equals(p.getDomiciliario().getId())) {
                p.setDomiciliario(null);
                pedidoRepository.save(p);
            }
        }
        domiciliarioRepository.delete(domiciliario);
    }
}
