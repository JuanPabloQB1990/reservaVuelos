package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.excepcion.UserNotFoundException;
import com.proyecto.reservaVuelos.models.VuelosModel;
import com.proyecto.reservaVuelos.services.VuelosService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "v1/flights")
public class VuelosController {

    @Autowired
    VuelosService vuelosService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveFlight(@RequestBody @Valid VuelosModel vuelo){
        return this.vuelosService.saveFlight(vuelo);
    }

    @GetMapping(path="{idVuelo}")
    public Map<String, Object> getFlight(@PathVariable("idVuelo") Long idVuelo) throws UserNotFoundException {
        return this.vuelosService.getFlightById(idVuelo);
    }

    @PutMapping(path="{idFlight}")
    public ResponseEntity<Object> updateFlight(@PathVariable("idFlight") Long idFlight, @RequestBody VuelosModel editFlight){
        return this.vuelosService.updateFlight(idFlight, editFlight);
    }

    @GetMapping
    public ArrayList<VuelosModel> getFlightsByCriterium(@RequestParam("origen") String origen, @RequestParam("destino") String destino, @RequestParam("fechaPartida") @Nullable LocalDate fechaPartida){
        if (fechaPartida == null){
            return this.vuelosService.getAllFlightsByWithOutDate(origen, destino);
        }
        return this.vuelosService.getAllFlightsByWithDate(origen, destino, fechaPartida);

    }

    @DeleteMapping(path = "{idFlight}")
    public String deleteFlightById(@PathVariable("idFlight") Long idFlight){
        boolean deleteOk = this.vuelosService.deleteFlightById(idFlight);
        if (deleteOk){
            return "El vuelo se ha eliminado correctamente";
        }else{
            return "Hubo un error en la eliminacion";
        }
    }
}
