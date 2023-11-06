package com.proyecto.reservaVuelos.dto;

import com.proyecto.reservaVuelos.models.VueloModel;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EscalaModelList {
    private ArrayList<VueloModel> vuelos;

    public EscalaModelList(ArrayList<VueloModel> vuelos) {
        this.vuelos = vuelos;
    }

    public ArrayList<VueloModel> getVuelos() {
        return vuelos;
    }

    public void setVuelos(ArrayList<VueloModel> vuelos) {
        this.vuelos = vuelos;
    }
}
