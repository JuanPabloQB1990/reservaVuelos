package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
            String apellidoPasajero,
            Long numeroAsientosReservar) throws EntityNotFoundException {
        //Obtener el vuelo y pasajero correspondientes - Creo metodo en vuelo y pasajero
        VueloModel vuelo = vueloService.getFlightByCodigo(codigoVuelo);
        ClienteModel pasajero = clienteService.getPasajeroByNombre(primerNombrePasajero, apellidoPasajero);


        String codigoReservacion = generarCodigoReservacion();

        int numeroReservacion = generarNumeroReservacion();

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

    public void eliminarReservacionPorCliente(String primerNombrePasajero, String apellidoPasajero) throws EntityNotFoundException {
        // Obtener la lista de reservaciones del cliente con el nombre y apellido proporcionados
        List<ReservacionModel> reservaciones = reservacionRepository.findByCliente_PrimerNombreAndPasajero_Apellido(primerNombrePasajero, apellidoPasajero);

        if (!reservaciones.isEmpty()) {
            // Eliminar cada reserva encontrada
            for (ReservacionModel reservacion : reservaciones) {
                reservacionRepository.delete(reservacion);
            }
        } else {
            throw new EntityNotFoundException("No se encontraron reservaciones para el cliente especificado");
        }
    }

    public void actualizarReservacionPorCliente(String primerNombrePasajero, String apellidoPasajero, ReservacionModel nuevaReservacion) throws EntityNotFoundException {
        // Obtener la lista de reservaciones del cliente con el nombre y apellido proporcionados
        List<ReservacionModel> reservaciones = reservacionRepository.findByPasajero_PrimerNombreAndPasajero_Apellido(primerNombrePasajero, apellidoPasajero);

        if (!reservaciones.isEmpty()) {
            // Actualizar cada reserva encontrada con los nuevos datos
            for (ReservacionModel reservacion : reservaciones) {
                reservacion.setCodigoReservacion(nuevaReservacion.getCodigoReservacion());
                reservacion.setFechaReservacion(nuevaReservacion.getFechaReservacion());
                // Puedes actualizar otros campos según tus necesidades
                // ...

                reservacionRepository.save(reservacion); // Guardar la reserva actualizada
            }
        } else {
            throw new EntityNotFoundException("No se encontraron reservaciones para el cliente especificado");
        }
    }


    private String generarCodigoReservacion() {
        return UUID.randomUUID().toString();
    }

    private int generarNumeroReservacion() {
        return Integer.parseInt("12345"); //Ejemplo
    }
}
