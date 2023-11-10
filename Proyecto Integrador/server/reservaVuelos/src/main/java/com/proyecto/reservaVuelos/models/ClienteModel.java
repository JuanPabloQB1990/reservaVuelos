package com.proyecto.reservaVuelos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor

public class ClienteModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCliente;

    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String telefono;
    @Column
    private String correo;
    @Column
    private String pais;
    @Column
    private String ciudad;
    @Column
    private String direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReservacionModel> reservaciones = new ArrayList<>();

}
