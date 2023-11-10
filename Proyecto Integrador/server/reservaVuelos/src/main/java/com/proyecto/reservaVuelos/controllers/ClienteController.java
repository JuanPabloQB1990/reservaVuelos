package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping(path = "{idCliente}")
    public ClienteModel obtenerPasajeros(@PathVariable Long idCliente){
        return this.clienteService.obtenerClientePorId(idCliente);
    }

    @PostMapping
    public ResponseEntity<Object> registrarCliente(@RequestBody ClienteModel cliente){
        return this.clienteService.registrarCliente(cliente);
    }
}
