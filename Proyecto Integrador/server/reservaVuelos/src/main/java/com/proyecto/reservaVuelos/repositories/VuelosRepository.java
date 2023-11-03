package com.proyecto.reservaVuelos.repositories;

import com.proyecto.reservaVuelos.models.VuelosModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public interface VuelosRepository extends JpaRepository<VuelosModel, Long> {

    @Transactional
    @Modifying
    @Query(value = "{call update_vuelo(:idVuelo, :origen, :destino, :fechaPartida, :fechaLlegada, :precio, :asientos, :idTipoVuelo, :idAerolinea)}", nativeQuery = true)
    void actualizarVuelo(@Param("idVuelo")Long idVuelo,
                         @Param("origen")String origen,
                         @Param("destino")String destino,
                         @Param("fechaPartida") LocalDateTime fechaPartida,
                         @Param("fechaLlegada")LocalDateTime fechaLlegada,
                         @Param("precio")Double precio,
                         @Param("asientos")Long asientos,
                         @Param("idTipoVuelo")Long idTipoVuelo,
                         @Param("idAerolinea")Long idAerolinea);

    @Transactional
    @Modifying
    @Query(value = "{call insert_vuelo(:origen, :destino, :fechaPartida, :fechaLlegada, :precio, :asientos, :idTipoVuelo, :idAerolinea)}", nativeQuery = true)
    void crearVuelo(@Param("origen")String origen,
                    @Param("destino")String destino,
                    @Param("fechaPartida") LocalDateTime fechaPartida,
                    @Param("fechaLlegada")LocalDateTime fechaLlegada,
                    @Param("precio")Double precio,
                    @Param("asientos")Long asientos,
                    @Param("idTipoVuelo")Long idTipoVuelo,
                    @Param("idAerolinea")Long idAerolinea);

    @Query(value = "select * from vuelos where origen = :origen and destino = :destino and date(fechaPartida) = :fechaPartida",
            nativeQuery = true)
    Page<VuelosModel> mostrarVuelosPorCriterioConFecha(@Param("origen")String origen,
                                                       @Param("destino")String destino,
                                                       @Param("fechaPartida") LocalDate fechaPartida,
                                                       Pageable pageable);

    @Query(value = "select * from vuelos where origen = :origen and destino = :destino",
            nativeQuery = true)
    Page<VuelosModel> mostrarVuelosPorCriterioSinFecha(@Param("origen")String origen,
                                                       @Param("destino")String destino, Pageable pageable);
    Optional<VuelosModel> findByCodigoVuelo(String codigoVuelo);
}
