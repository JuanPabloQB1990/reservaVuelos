package com.proyecto.reservaVuelos.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class CrearReservaDto {

    private Long idVuelo1;

    @Nullable
    private Long idVuelo2;

    @Nullable
    private Long idVuelo3;

    private int asientos;
    private Long idCliente;

}
