package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    Optional<ClienteModel> findByNombreAndApellido(String primerNombre, String apellido);
}
