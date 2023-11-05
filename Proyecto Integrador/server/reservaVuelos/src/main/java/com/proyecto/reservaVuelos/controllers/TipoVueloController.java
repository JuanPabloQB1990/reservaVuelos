package com.proyecto.reservaVuelos.controllers;

import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.TipoVueloModel;
import com.proyecto.reservaVuelos.services.TipoVueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1/typeFlights")
public class TipoVueloController {

    private TipoVueloService tipoVueloService;

    @Autowired
    public TipoVueloController(TipoVueloService tipoVueloService) {
        this.tipoVueloService = tipoVueloService;
    }

    @GetMapping
    public List<TipoVueloModel> getTipoVuelos(){
        return this.tipoVueloService.getTipoVuelos();
    }

    @PostMapping
    public void agregarTipoVuelo(@RequestBody TipoVueloModel tipoVuelo){
        this.tipoVueloService.saveTipoVuelo(tipoVuelo);

    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> editarTipoVuelo(@PathVariable Long id, @RequestBody TipoVueloModel editTipoVuelo) throws EntityNotFoundException {
        return this.tipoVueloService.editarTipoVuelo(id, editTipoVuelo);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> eliminarTipoVuelo(@PathVariable Long id) throws EntityNotFoundException {
        return this.tipoVueloService.eliminarTipoVuelo(id);
    }
}
