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

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(path="{idVuelo}")
    public VueloModelList obtenerVueloPorId(@PathVariable("idVuelo") Long idVuelo) throws EntityNotFoundException {
        return this.vueloService.obtenerVueloPorId(idVuelo);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(path = "all")
    public Page<VueloModelList> obtenerTodosLosVuelos(Pageable pageable) throws EntityNotFoundException {
        return this.vueloService.obtenerTodosLosVuelos(pageable);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    public Stack<List<VueloModelList>> obtenerVuelosPorCriterio(
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaPartida") @Nullable LocalDate fechaPartida) throws EntityNotFoundException {

        if (fechaPartida == null) {
            return this.vueloService.obtenerTodosLosVuelosSinFecha(origen, destino);
        }
        return this.vueloService.obtenerTodosLosVuelosConFecha(origen, destino, fechaPartida);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> crearVuelo(@RequestBody @Valid VueloModel vuelo){
        return this.vueloService.crearVuelo(vuelo);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping(path="{idVuelo}")
    public ResponseEntity<Object> actualizarVuelo(@PathVariable("idVuelo") Long idVuelo, @RequestBody VueloModel editVuelo) throws EntityNotFoundException {
        return this.vueloService.actualizarVuelo(idVuelo, editVuelo);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping(path = "{idVuelo}")
    public ResponseEntity<Object> eliminarVueloPorId(@PathVariable("idVuelo") Long idVuelo) throws EntityNotFoundException {
        return this.vueloService.eliminarVueloPorId(idVuelo);

    }
}
