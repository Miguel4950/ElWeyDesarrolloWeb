package com.elwey.restaurante.repository;

import com.elwey.restaurante.entities.Domiciliario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomiciliarioRepository extends JpaRepository<Domiciliario, Long> {
    List<Domiciliario> findByDisponible(Boolean disponible);
}
