package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.models.PasajerosModel;
import com.proyecto.reservaVuelos.repositories.PasajerosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PasajerosService {

    @Autowired
    PasajerosRepository pasajerosRepository;

    public ArrayList<PasajerosModel> getPasajeros(){
        return (ArrayList<PasajerosModel>) pasajerosRepository.findAll();
        //return List.of(new ModeloPasajeros(1,"Bendite", "Rutty", "1421833425", "bruttye@sitemeter.com", "Reino Unido", "Manchester", "44159 Rutledge Avenue"));
    }
}
