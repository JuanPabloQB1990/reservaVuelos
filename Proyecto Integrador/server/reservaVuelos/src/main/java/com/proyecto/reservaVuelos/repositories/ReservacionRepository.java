package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.ReservacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservacionRepository extends JpaRepository<ReservacionModel, Long> {
}

