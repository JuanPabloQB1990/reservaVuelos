package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.models.AerolineaModel;
import com.proyecto.reservaVuelos.services.AerolineasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "v1/aerolineas")
public class AerolineasController {
    @Autowired
    AerolineasService aerolineasService;

    @GetMapping
    public List<AerolineaModel> getAerolineas(){
        return this.aerolineasService.getAerolineas();
    }

    @PostMapping
    public void agregarAerolinea(@RequestBody AerolineaModel aerolinea){
        this.aerolineasService.saveAerolinea(aerolinea);

    }
}
