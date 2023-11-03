package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.services.PasajerosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/pasajeros")
public class ClienteController {

    private PasajerosService pasajerosService;

    @Autowired
    public ClienteController(PasajerosService pasajerosService) {
        this.pasajerosService = pasajerosService;
    }

    @GetMapping
    public ArrayList<ClienteModel> obtenerPasajeros(){
        return pasajerosService.getPasajeros();
    }
}
