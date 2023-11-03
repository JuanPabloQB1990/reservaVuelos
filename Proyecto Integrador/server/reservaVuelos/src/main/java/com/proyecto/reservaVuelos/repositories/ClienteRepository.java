package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
}
