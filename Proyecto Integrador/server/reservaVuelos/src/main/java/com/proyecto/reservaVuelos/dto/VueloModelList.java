package com.proyecto.reservaVuelos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.reservaVuelos.models.AerolineaModel;
import com.proyecto.reservaVuelos.models.TipoVueloModel;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
public class VueloModelList {

    private String codVuelo;

    private String origen;

    private String destino;

    private LocalDateTime fechaPartida;

    private LocalDateTime fechaLlegada;

    private double precio;

    private long asientos;

    private String tipoVuelo;

    private String aerolinea;
}
