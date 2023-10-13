package com.proyecto.reservaVuelos.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipo_vuelos")
public class TipoVueloModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idTipoVuelo;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "tipoVuelo", cascade = CascadeType.ALL)
    private List<VuelosModel> vuelos = new ArrayList<>();

    public Long getIdTipoVuelo() {
        return idTipoVuelo;
    }

    public void setIdTipoVuelo(Long idTipoVuelo) {
        this.idTipoVuelo = idTipoVuelo;
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
            vuelo.setTipoVuelo(this);
        }
    }
}
