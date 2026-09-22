package com.elwey.restaurante.service;

import com.elwey.restaurante.entities.Domiciliario;
import com.elwey.restaurante.errors.DomiciliarioNotFoundException;
import com.elwey.restaurante.repository.DomiciliarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DomiciliarioServiceImpl implements DomiciliarioService {

    @Autowired
    private DomiciliarioRepository domiciliarioRepository;

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
        domiciliarioRepository.save(domiciliario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Domiciliario domiciliario = searchById(id);
        domiciliarioRepository.delete(domiciliario);
    }
}
