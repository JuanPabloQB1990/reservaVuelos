package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.models.PasajerosModel;
import com.proyecto.reservaVuelos.services.PasajerosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/pasajeros")
public class PasajerosController {

    private PasajerosService pasajerosService;

    @Autowired
    public PasajerosController(PasajerosService pasajerosService) {
        this.pasajerosService = pasajerosService;
    }

    @GetMapping
    public ArrayList<PasajerosModel> obtenerPasajeros(){
        return pasajerosService.getPasajeros();
    }
}
