package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservacionService {

    private final ReservacionRepository reservacionRepository;
    private final VueloService vueloService;
    private final ClienteService clienteService;

    @Autowired
    public ReservacionService(
            ReservacionRepository reservacionRepository,
            VueloService vueloService,
            ClienteService clienteService
    ) {
        this.reservacionRepository = reservacionRepository;
        this.vueloService = vueloService;
        this.clienteService = clienteService;
    }

    public ReservacionModel crearReservacion(
            String codigoVuelo,
            LocalDateTime fechaReservacion,
            String primerNombrePasajero,
            String apellidoPasajero
    ) throws EntityNotFoundException {
        //Obtener el vuelo y pasajero correspondientes - Cree metodo en vuelo y pasajero
        VueloModel vuelo = vueloService.getFlightByCodigo(codigoVuelo);
        ClienteModel pasajero = clienteService.getPasajeroByNombre(primerNombrePasajero, apellidoPasajero);


        String codigoReservacion = generarCodigoReservacion();

        String numeroReservacion = generarNumeroReservacion();

        // Creacion
        ReservacionModel reservacion = new ReservacionModel(
                codigoReservacion,
                vuelo,
                fechaReservacion,
                numeroReservacion,
                pasajero
        );

        // Guardar la reserva en la base de datos - Creo metodo en ReservacionRepository
        return reservacionRepository.save(reservacion);
    }

    private String generarCodigoReservacion() {
        return UUID.randomUUID().toString();
    }

    private String generarNumeroReservacion() {
        return "12345"; //Ejemplo
    }
}
