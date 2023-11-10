package com.proyecto.reservaVuelos.dto;

import com.proyecto.reservaVuelos.models.VueloModel;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EscalaModelList {
    private List<VueloModelList> vuelos;

    public EscalaModelList(List<VueloModelList> vuelos) {
        this.vuelos = vuelos;
    }

    public List<VueloModelList> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<VueloModelList> vuelos) {
        this.vuelos = vuelos;
    }
}
