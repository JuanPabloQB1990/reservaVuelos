package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.services.VueloService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "v1/flights")
public class VueloController {

    private VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping(path="{idVuelo}")
    public VueloModelList getFlight(@PathVariable("idVuelo") Long idVuelo) throws EntityNotFoundException {
        return this.vueloService.getFlightById(idVuelo);
    }

    @GetMapping
    public Page<VueloModel> getFlightsByCriterium(
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaPartida") @Nullable LocalDate fechaPartida,
            @RequestParam("page") int page,
            @RequestParam("size") int size) throws EntityNotFoundException {
        Pageable pageRequest = PageRequest.of(page, size);
        if (fechaPartida == null){
            return this.vueloService.getAllFlightsByWithOutDate(origen, destino, pageRequest);
        }
        return this.vueloService.getAllFlightsByWithDate(origen, destino, fechaPartida, pageRequest);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveFlight(@RequestBody @Valid VueloModel vuelo){
        return this.vueloService.saveFlight(vuelo);
    }

    @PutMapping(path="{idFlight}")
    public ResponseEntity<Object> updateFlight(@PathVariable("idFlight") Long idVuelo, @RequestBody VueloModel editVuelo) throws EntityNotFoundException {
        return this.vueloService.updateFlight(idVuelo, editVuelo);
    }

    @DeleteMapping(path = "{idFlight}")
    public ResponseEntity<Object> deleteFlightById(@PathVariable("idFlight") Long idVuelo) throws EntityNotFoundException {
        return this.vueloService.deleteFlightById(idVuelo);

    }
}
