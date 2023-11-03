package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VuelosModel;
import com.proyecto.reservaVuelos.services.VuelosService;
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
public class VuelosController {

    private VuelosService vuelosService;

    @Autowired
    public VuelosController(VuelosService vuelosService) {
        this.vuelosService = vuelosService;
    }

    @GetMapping(path="{idVuelo}")
    public VueloModelList getFlight(@PathVariable("idVuelo") Long idVuelo) throws EntityNotFoundException {
        return this.vuelosService.getFlightById(idVuelo);
    }

    @GetMapping
    public Page<VuelosModel> getFlightsByCriterium(
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaPartida") @Nullable LocalDate fechaPartida,
            @RequestParam("page") int page,
            @RequestParam("size") int size) throws EntityNotFoundException {
        Pageable pageRequest = PageRequest.of(page, size);
        if (fechaPartida == null){
            return this.vuelosService.getAllFlightsByWithOutDate(origen, destino, pageRequest);
        }
        return this.vuelosService.getAllFlightsByWithDate(origen, destino, fechaPartida, pageRequest);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveFlight(@RequestBody @Valid VuelosModel vuelo){
        return this.vuelosService.saveFlight(vuelo);
    }

    @PutMapping(path="{idFlight}")
    public ResponseEntity<Object> updateFlight(@PathVariable("idFlight") Long idVuelo, @RequestBody VuelosModel editVuelo) throws EntityNotFoundException {
        return this.vuelosService.updateFlight(idVuelo, editVuelo);
    }

    @DeleteMapping(path = "{idFlight}")
    public ResponseEntity<Object> deleteFlightById(@PathVariable("idFlight") Long idVuelo) throws EntityNotFoundException {
        return this.vuelosService.deleteFlightById(idVuelo);

    }
}
