package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.AerolineaModel;
import com.proyecto.reservaVuelos.services.AerolineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/aerolineas")
public class AerolineaController {
    private AerolineaService aerolineaService;

    @Autowired
    public AerolineaController(AerolineaService aerolineaService) {
        this.aerolineaService = aerolineaService;
    }

    @GetMapping
    public List<AerolineaModel> getAerolineas(){
        return this.aerolineaService.getAerolineas();
    }

    @PostMapping
    public void agregarAerolinea(@RequestBody AerolineaModel aerolinea){
        this.aerolineaService.saveAerolinea(aerolinea);

    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> editarAerolinea(@PathVariable Long id, @RequestBody AerolineaModel editAerolinea) throws EntityNotFoundException {
        return this.aerolineaService.editarAerolinea(id, editAerolinea);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> eliminarAerolinea(@PathVariable Long id) throws EntityNotFoundException {
        return this.aerolineaService.eliminarAerolinea(id);
    }
}
