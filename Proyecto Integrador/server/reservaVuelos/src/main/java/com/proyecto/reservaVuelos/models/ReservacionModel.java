package com.proyecto.reservaVuelos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "idVuelo1")
    private VueloModel vuelo1;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idVuelo2")
    private VueloModel vuelo2;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idVuelo3")
    private VueloModel vuelo3;

    @NotNull(message = "el numero de asientos del vuelo es obligatorio")
    @Min(1)
    private long asientos;

    private LocalDateTime fechaReservacion;

    private int numeroAsientos;

    public void setFechaReservacion(LocalDateTime nuevaFechaReservacion) {
        this.fechaReservacion = nuevaFechaReservacion;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCliente")
    private ClienteModel cliente;

    public ReservacionModel(String codigoReservacion,
                            VueloModel vuelo1,
                            LocalDateTime fechaReservacion,
                            int numeroAsientos,
                            ClienteModel cliente) {
        this.codigoReservacion = codigoReservacion;
        this.vuelo1 = vuelo1;
        this.fechaReservacion = fechaReservacion;
        this.numeroAsientos = numeroAsientos;
        this.cliente = cliente;
    }

    public ReservacionModel(String codigoReservacion,
                            VueloModel vuelo1,
                            VueloModel vuelo2,
                            LocalDateTime fechaReservacion,
                            int numeroAsientos,
                            ClienteModel cliente) {
        this.codigoReservacion = codigoReservacion;
        this.vuelo1 = vuelo1;
        this.vuelo2 = vuelo2;
        this.fechaReservacion = fechaReservacion;
        this.numeroAsientos = numeroAsientos;
        this.cliente = cliente;
    }

    public ReservacionModel(String codigoReservacion,
                            VueloModel vuelo1,
                            VueloModel vuelo2,
                            VueloModel vuelo3,
                            LocalDateTime fechaReservacion,
                            int numeroAsientos,
                            ClienteModel cliente) {
        this.codigoReservacion = codigoReservacion;
        this.vuelo1 = vuelo1;
        this.vuelo2 = vuelo2;
        this.vuelo3 = vuelo3;
        this.fechaReservacion = fechaReservacion;
        this.numeroAsientos = numeroAsientos;
        this.cliente = cliente;
    }




}
