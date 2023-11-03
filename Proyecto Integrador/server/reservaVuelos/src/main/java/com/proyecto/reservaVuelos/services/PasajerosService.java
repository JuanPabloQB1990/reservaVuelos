package com.proyecto.reservaVuelos.services;


import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.repositories.ClienteRepository;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PasajerosService {

    @Autowired
    ClienteRepository clienteRepository;

    public ArrayList<ClienteModel> getPasajeros(){
        return (ArrayList<ClienteModel>) clienteRepository.findAll();
        //return List.of(new ModeloPasajeros(1,"Bendite", "Rutty", "1421833425", "bruttye@sitemeter.com", "Reino Unido", "Manchester", "44159 Rutledge Avenue"));
    }

    public ClienteModel getPasajeroByNombre(String primerNombre, String apellido) throws EntityNotFoundException {
        Optional<ClienteModel> pasajeroEncontrado = clienteRepository.findByNombreAndApellido(primerNombre, apellido);

        if (pasajeroEncontrado.isPresent()) {
            return pasajeroEncontrado.get();
        } else {
            throw new EntityNotFoundException("Pasajero no encontrado");
        }
    }

}

