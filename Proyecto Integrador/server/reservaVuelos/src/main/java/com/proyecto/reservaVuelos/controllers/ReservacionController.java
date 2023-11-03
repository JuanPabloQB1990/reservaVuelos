package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.services.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservaciones")
public class ReservacionController {

    private ReservacionService reservacionService;

    @Autowired
    public ReservacionController(ReservacionService reservacionService) {
        this.reservacionService = reservacionService;
    }

    @PostMapping
    public ResponseEntity<Object> crearReservacion(@RequestBody ReservacionModel reservacionModel) {
        try {
            ReservacionModel reservacion = reservacionService.crearReservacion(
                    reservacionModel.getCodigoReservacion(),
                    reservacionModel.getFechaReservacion(),
                    reservacionModel.getPasajero().getNombre(),
                    reservacionModel.getPasajero().getApellido()
            );
            return ResponseEntity.ok(reservacion);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al crear la reservación: " + e.getMessage());
        } catch (Exception ex) {
            // Otra excepcion de servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}
