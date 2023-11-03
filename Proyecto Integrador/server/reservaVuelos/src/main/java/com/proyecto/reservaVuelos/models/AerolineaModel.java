package com.proyecto.reservaVuelos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "aerolineas")
@AllArgsConstructor
@NoArgsConstructor
public class AerolineaModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idAerolinea;

    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aerolinea", cascade = CascadeType.ALL)
    private List<VuelosModel> vuelos = new ArrayList<>();


}
