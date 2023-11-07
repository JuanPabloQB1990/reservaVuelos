package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.services.VueloService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping(path = "v1/flights")
public class VueloController {

    private VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping(path="{idVuelo}")
    public VueloModelList obtenerVueloPorId(@PathVariable("idVuelo") Long idVuelo) throws EntityNotFoundException {
        return this.vueloService.obtenerVueloPorId(idVuelo);
    }

    @GetMapping(path = "all")
    public List<VueloModelList> obtenerTodosLosVuelos(){
        return this.vueloService.obtenerTodosLosVuelos();
    }

    @GetMapping
    public Stack<List<VueloModelList>> getFlightsByCriterium(
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaPartida") @Nullable LocalDate fechaPartida) throws EntityNotFoundException {
        //Pageable pageRequest = PageRequest.of(page, size);
        if (fechaPartida == null){
            return this.vueloService.obtenerTodosLosVuelosSinFecha(origen, destino);
        }
        return this.vueloService.obtenerTodosLosVuelosConFecha(origen, destino, fechaPartida);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> crearVuelo(@RequestBody @Valid VueloModel vuelo){
        return this.vueloService.crearVuelo(vuelo);
    }

    @PutMapping(path="{idFlight}")
    public ResponseEntity<Object> actualizarVuelo(@PathVariable("idFlight") Long idVuelo, @RequestBody VueloModel editVuelo) throws EntityNotFoundException {
        return this.vueloService.actualizarVuelo(idVuelo, editVuelo);
    }

    @DeleteMapping(path = "{idFlight}")
    public ResponseEntity<Object> eliminarVueloPorId(@PathVariable("idFlight") Long idVuelo) throws EntityNotFoundException {
        return this.vueloService.eliminarVueloPorId(idVuelo);

    }
}
