package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PasajerosService {

    @Autowired
    ClienteRepository clienteRepository;

    public ArrayList<ClienteModel> getPasajeros(){
        return (ArrayList<ClienteModel>) clienteRepository.findAll();
        //return List.of(new ModeloPasajeros(1,"Bendite", "Rutty", "1421833425", "bruttye@sitemeter.com", "Reino Unido", "Manchester", "44159 Rutledge Avenue"));
    }
}
