package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.models.AerolineaModel;
import com.proyecto.reservaVuelos.repositories.AerolineasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AerolineasService {

    @Autowired
    AerolineasRepository aerolineasRepository;


    public List<AerolineaModel> getAerolineas(){
        return this.aerolineasRepository.findAll();
    }

    public void saveAerolinea(AerolineaModel aerolinea){
        this.aerolineasRepository.save(aerolinea);
    }
}
