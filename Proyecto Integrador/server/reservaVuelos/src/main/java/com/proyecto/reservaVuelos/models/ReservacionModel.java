package com.proyecto.reservaVuelos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@Entity
@Table(name = "reservaciones")
@AllArgsConstructor
@NoArgsConstructor
public class ReservacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservacion;

    private String codigoReservacion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idVuelo")
    private VueloModel vuelo;

    private LocalDateTime fechaReservacion;

    private String numeroReservacion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPasajero")
    private ClienteModel pasajero;

    public ReservacionModel(String codigoReservacion, VueloModel vuelo, LocalDateTime fechaReservacion, String numeroReservacion, ClienteModel pasajero) {
        this.codigoReservacion = codigoReservacion;
        this.vuelo = vuelo;
        this.fechaReservacion = fechaReservacion;
        this.numeroReservacion = numeroReservacion;
        this.pasajero = pasajero;
    }

    public String getCodigoReservacion() {
        return codigoReservacion;
    }

    public void setCodigoReservacion(String codigoReservacion) {
        this.codigoReservacion = codigoReservacion;
    }

    public VueloModel getVuelo() {
        return vuelo;
    }

    public void setVuelo(VueloModel vuelo) {
        this.vuelo = vuelo;
    }

    public LocalDateTime getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(LocalDateTime fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    public String getNumeroReservacion() {
        return numeroReservacion;
    }

    public void setNumeroReservacion(String numeroReservacion) {
        this.numeroReservacion = numeroReservacion;
    }

    public ClienteModel getPasajero() {
        return pasajero;
    }

    public void setPasajero(ClienteModel pasajero) {
        this.pasajero = pasajero;
    }
}
