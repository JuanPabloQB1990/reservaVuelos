package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.PasajerosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasajerosRepository extends JpaRepository<PasajerosModel, Long> {
}
