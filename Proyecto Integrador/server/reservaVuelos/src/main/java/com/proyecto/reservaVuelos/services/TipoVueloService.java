package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.models.AerolineaModel;
import com.proyecto.reservaVuelos.models.TipoVueloModel;
import com.proyecto.reservaVuelos.repositories.AerolineasRepository;
import com.proyecto.reservaVuelos.repositories.TipoVueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoVueloService {

    @Autowired
    TipoVueloRepository tipoVueloRepository;


    public List<TipoVueloModel> getTipoVuelos(){
        return this.tipoVueloRepository.findAll();
    }

    public void saveTipoVuelo(TipoVueloModel tipoVuelo){
        this.tipoVueloRepository.save(tipoVuelo);
    }
}
