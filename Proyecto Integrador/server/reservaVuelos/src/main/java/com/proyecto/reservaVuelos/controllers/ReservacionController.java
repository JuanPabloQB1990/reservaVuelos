package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.dto.CrearReservaDto;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.services.ReservacionService;
import com.proyecto.reservaVuelos.services.VueloService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<Object> crearReservacion(@RequestBody CrearReservaDto reserva) {
        return reservacionService.crearReservacion(reserva);
    }

    @DeleteMapping("{idReservacion}")
    public ResponseEntity<Object> cancelarReservacionPorId(@PathVariable Long idReservacion1, @PathVariable @Nullable Long idReservacion2, @PathVariable @Nullable Long idReservacion3) {
        try {
             return reservacionService.cancelarReservacionPorId(idReservacion1, idReservacion2, idReservacion3);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al eliminar las reservaciones: " + e.getMessage());
        }
    }
    @PutMapping("/{idReservacion}")
    public ResponseEntity<Object> actualizarReservacion(
            @PathVariable Long idReservacion,
            @RequestBody ReservacionModel reservacionActualizada) {

        try {
            ReservacionModel resultadoActualizacion = reservacionService.actualizarReservacion(idReservacion, reservacionActualizada.getFechaReservacion());

            if (resultadoActualizacion != null) {
                return ResponseEntity.ok(resultadoActualizacion);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al actualizar la reservación: " + e.getMessage());
        }
    }



    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @GetMapping(path = "{idReserva}")
    public ReservacionModel obtenerReservaPorId(@PathVariable Long idReserva){
        return this.reservacionService.obtenerReservaPorId(idReserva);
    }

    @GetMapping(path = "cliente/{idCliente}")
    public List<ReservacionModel> obtenerReservacionesPorIdCliente(@PathVariable Long idCliente) throws EntityNotFoundException {
        return this.reservacionService.obtenerReservacionesPorIdCliente(idCliente);
    }
}