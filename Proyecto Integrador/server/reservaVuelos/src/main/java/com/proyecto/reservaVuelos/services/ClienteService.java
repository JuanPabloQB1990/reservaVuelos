package com.proyecto.reservaVuelos.services;


import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.repositories.ClienteRepository;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteModel obtenerClientePorId(Long idCliente){
        return this.clienteRepository.findById(idCliente).get();
    }

    public ResponseEntity<Object> registrarCliente(ClienteModel cliente) {

        ClienteModel clienteRegistrado = this.clienteRepository.save(cliente);

        return new ResponseEntity("cliente creado", HttpStatusCode.valueOf(200));
    }
}

