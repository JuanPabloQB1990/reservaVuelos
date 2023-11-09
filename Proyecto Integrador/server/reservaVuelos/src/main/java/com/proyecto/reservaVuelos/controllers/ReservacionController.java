package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.services.ReservacionService;
import com.proyecto.reservaVuelos.services.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservaciones")
public class ReservacionController {


    private ReservacionService reservacionService;
    private VueloService vueloService;

    @Autowired
    public ReservacionController(ReservacionService reservacionService, VueloService vueloService) {
        this.reservacionService = reservacionService;
        this.vueloService = vueloService;
    }

    @PostMapping
    public ResponseEntity<Object> crearReservacion(@RequestBody ReservacionModel reservacionModel) {
        try {
            LocalDateTime fechaSalida = reservacionModel.getFechaReservacion();
            Long numeroAsientosReservar = reservacionModel.getVuelo().getAsientos();

            // Verificar que la reserva se realice con al menos 3 horas de anticipación
            if (fechaSalida.minusHours(3).isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("La reserva debe realizarse con al menos 3 horas de anticipación.");
            } else {
                // Obtener el vuelo correspondiente a la reserva
                VueloModel vuelo = vueloService.getFlightByCodigo(reservacionModel.getVuelo().getCodigoVuelo());

                // Verificar si hay suficientes asientos disponibles
                if (vuelo.getAsientos() >= numeroAsientosReservar) {
                    // Realizar la reserva
                    ReservacionModel reservacion = reservacionService.crearReservacion(
                            reservacionModel.getCodigoReservacion(),
                            reservacionModel.getFechaReservacion(),
                            reservacionModel.getPasajero().getNombre(),
                            reservacionModel.getPasajero().getApellido(),
                            numeroAsientosReservar
                    );

                    // Actualizar el número de asientos disponibles en el vuelo
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
            // Otra excepción de servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }



    @DeleteMapping("/eliminarReservacionPorCliente")
    public ResponseEntity<String> eliminarReservacionPorCliente(
            @RequestParam("primerNombrePasajero") String primerNombrePasajero,
            @RequestParam("apellidoPasajero") String apellidoPasajero
    ) {
        try {
            reservacionService.eliminarReservacionPorCliente(primerNombrePasajero, apellidoPasajero);
            return ResponseEntity.ok("Reservaciones eliminadas correctamente para el cliente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al eliminar las reservaciones: " + e.getMessage());
        }
    }

    @PutMapping("/actualizarReservacionPorCliente")
    public ResponseEntity<String> actualizarReservacionPorCliente(
            @RequestParam("primerNombrePasajero") String primerNombrePasajero,
            @RequestParam("apellidoPasajero") String apellidoPasajero,
            @RequestBody ReservacionModel nuevaReservacion
    ) {
        try {
            reservacionService.actualizarReservacionPorCliente(primerNombrePasajero, apellidoPasajero, nuevaReservacion);
            return ResponseEntity.ok("Reservación actualizada correctamente para el cliente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al actualizar la reservación: " + e.getMessage());
        }
    }
}
