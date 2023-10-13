package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.models.AerolineaModel;
import com.proyecto.reservaVuelos.models.TipoVueloModel;
import com.proyecto.reservaVuelos.services.AerolineasService;
import com.proyecto.reservaVuelos.services.TipoVueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/typeFlights")
public class TipoVueloController {
    @Autowired
    TipoVueloService tipoVueloService;

    @GetMapping
    public List<TipoVueloModel> getTipoVuelos(){
        return this.tipoVueloService.getTipoVuelos();
    }

    @PostMapping
    public void agregarTipoVuelo(@RequestBody TipoVueloModel tipoVuelo){
        this.tipoVueloService.saveTipoVuelo(tipoVuelo);

    }
}
