package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.PasajerosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasajerosRepository extends JpaRepository<PasajerosModel, Long> {
    Optional<PasajerosModel> findByNombreAndApellido(String primerNombre, String apellido);

}
