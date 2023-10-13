package com.proyecto.reservaVuelos.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aerolineas")
public class AerolineaModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idAerolinea;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "aerolinea", cascade = CascadeType.ALL)
    private List<VuelosModel> vuelos = new ArrayList<>();

    public Long getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Long idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<VuelosModel> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<VuelosModel> vuelos) {
        this.vuelos = vuelos;
        for (VuelosModel vuelo : vuelos) {
            vuelo.setAerolinea(this);
        }
    }
}
