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
@AllArgsConstructor
public class VuelosModelDto {

    @NotNull(message = "el origen del vuelo es obligatorio")
    private String origen;

    @NotNull(message = "el destino del vuelo es obligatorio")
    private String destino;

    @NotNull(message = "la fecha de partida del vuelo es obligatorio")
    private LocalDateTime fechaPartida;

    @NotNull(message = "la fecha de llegada del vuelo es obligatorio")
    private LocalDateTime fechaLlegada;

    @NotNull(message = "el precio del vuelo es obligatorio")
    private double precio;

    @NotNull(message = "el numero de asientos del vuelo es obligatorio")
    @Min(1)
    private long asientos;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idTipoVuelo")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TipoVueloModel tipoVuelo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idAerolinea")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AerolineaModel aerolinea;
}
