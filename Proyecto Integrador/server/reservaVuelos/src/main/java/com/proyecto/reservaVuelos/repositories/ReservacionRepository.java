package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.ReservacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservacionRepository extends JpaRepository<ReservacionModel, Long> {
    List<ReservacionModel> findByCliente_PrimerNombreAndPasajero_Apellido(String primerNombrePasajero, String apellidoPasajero);

}

