package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ResponseEntity<Object> crearReservacion(@RequestBody ReservacionModel reservacionModel) {
        try {
            LocalDateTime fechaSalida = reservacionModel.getFechaReservacion();
            Long numeroAsientosReservar = reservacionModel.getVuelo().getAsientos();

            if (fechaSalida.minusHours(3).isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("La reserva debe realizarse con al menos 3 horas de anticipación.");
            } else {
                // Obtener el vuelo
                VueloModel vuelo = vueloService.getFlightByCodigo(reservacionModel.getVuelo().getCodigoVuelo());

                // Asientos disponibles
                if (vuelo.getAsientos() >= numeroAsientosReservar) {
                    // Obtener el pasajero correspondiente
                    ClienteModel pasajero = clienteService.getPasajeroByNombre(
                            reservacionModel.getPasajero().getNombre(),
                            reservacionModel.getPasajero().getApellido()
                    );

                    // Crear y guardar la reserva en la base de datos
                    String codigoReservacion = generarCodigoReservacion();
                    int numeroReservacion = generarNumeroReservacion();

                    ReservacionModel reservacion = new ReservacionModel(
                            codigoReservacion,
                            vuelo,
                            fechaSalida,
                            numeroReservacion,
                            pasajero
                    );

                    reservacionRepository.save(reservacion);

                    // Actualizar el número de asientos disponibles
                    vuelo.setAsientos(vuelo.getAsientos() - numeroAsientosReservar);
                    vueloService.updateFlight(vuelo.getIdVuelo(), vuelo);

                    System.out.println("Reserva realizada con éxito.");
                    return ResponseEntity.ok(reservacion);
                } else {
                    return ResponseEntity.badRequest().body("No hay suficientes asientos disponibles para este vuelo.");
                }
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al crear la reservación: " + e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    public void eliminarReservacionPorCliente(String primerNombrePasajero, String apellidoPasajero) throws EntityNotFoundException {
        // Obtener la lista de reservaciones
        List<ReservacionModel> reservaciones = reservacionRepository.findByCliente_PrimerNombreAndPasajero_Apellido(primerNombrePasajero, apellidoPasajero);

        if (!reservaciones.isEmpty()) {
            for (ReservacionModel reservacion : reservaciones) {
                reservacionRepository.delete(reservacion);
            }
        } else {
            throw new EntityNotFoundException("No se encontraron reservaciones para el cliente especificado");
        }
    }

    public void actualizarReservacionPorCliente(String primerNombrePasajero, String apellidoPasajero, ReservacionModel nuevaReservacion) throws EntityNotFoundException {
        List<ReservacionModel> reservaciones = reservacionRepository.findByPasajero_PrimerNombreAndPasajero_Apellido(primerNombrePasajero, apellidoPasajero);

        if (!reservaciones.isEmpty()) {
            // Actualizar cada reserva encontrada con los nuevos datos
            for (ReservacionModel reservacion : reservaciones) {
                reservacion.setCodigoReservacion(nuevaReservacion.getCodigoReservacion());
                reservacion.setFechaReservacion(nuevaReservacion.getFechaReservacion());

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
