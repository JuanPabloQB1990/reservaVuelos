package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.VuelosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface VuelosRepository extends JpaRepository<VuelosModel, Long> {

    @Transactional
    @Modifying
    @Query(value = "{call update_vuelo(:idVuelo, :origen, :destino, :fechaPartida, :fechaLlegada, :precio, :asientos, :idTipoVuelo, :idAerolinea)}", nativeQuery = true)
    int updateFlight(@Param("idVuelo")Long idVuelo, @Param("origen")String origen, @Param("destino")String destino, @Param("fechaPartida") LocalDateTime fechaPartida, @Param("fechaLlegada")LocalDateTime fechaLlegada, @Param("precio")Double precio,@Param("asientos")Long asientos,@Param("idTipoVuelo")Long idTipoVuelo,@Param("idAerolinea")Long idAerolinea);


    @Transactional
    @Modifying
    @Query(value = "{call insert_vuelo(:origen, :destino, :fechaPartida, :fechaLlegada, :precio, :asientos, :idTipoVuelo, :idAerolinea)}", nativeQuery = true)
    void insertFlight(@Param("origen")String origen, @Param("destino")String destino, @Param("fechaPartida") LocalDateTime fechaPartida, @Param("fechaLlegada")LocalDateTime fechaLlegada, @Param("precio")Double precio,@Param("asientos")Long asientos,@Param("idTipoVuelo")Long idTipoVuelo,@Param("idAerolinea")Long idAerolinea);

    @Query(value = "select * from vuelos where origen = :origen and destino = :destino and fechaPartida = :fechaPartida", nativeQuery = true)
    ArrayList<VuelosModel> mostrarVuelosPorCriterioConFechas(@Param("origen")String origen,@Param("destino")String destino,@Param("fechaPartida") LocalDate fechaPartida);


    @Query(value = "select * from vuelos where origen = :origen and destino = :destino", nativeQuery = true)
    ArrayList<VuelosModel> mostrarVuelosPorCriterioSinFechas(@Param("origen")String origen,@Param("destino")String destino);

    @Query(value = "select * from vuelos as v where idVuelo = :idVuelo", nativeQuery = true)
    VuelosModel buscarVueloPorId(@Param("idVuelo") Long idVuelo);
}
