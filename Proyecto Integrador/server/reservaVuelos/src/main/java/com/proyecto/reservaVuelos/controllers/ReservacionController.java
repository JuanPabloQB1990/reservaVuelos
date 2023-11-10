package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.services.ReservacionService;
import com.proyecto.reservaVuelos.services.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.ccache.MemoryCredentialsCache;

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
        return reservacionService.crearReservacion(reservacionModel);
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
