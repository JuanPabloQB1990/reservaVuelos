package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.dto.VuelosModelDto;
import com.proyecto.reservaVuelos.excepcion.UserNotFoundException;
import com.proyecto.reservaVuelos.models.VuelosModel;
import com.proyecto.reservaVuelos.repositories.AerolineasRepository;
import com.proyecto.reservaVuelos.repositories.TipoVueloRepository;
import com.proyecto.reservaVuelos.repositories.VuelosRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VuelosService {

    @Autowired
    VuelosRepository vuelosRepository;
    @Autowired
    TipoVueloRepository tipoVueloRepository;

    @Autowired
    AerolineasRepository aerolineasRepository;

    HashMap<String, Object> datos;

    public ResponseEntity<Object> updateFlight(Long idFlight, VuelosModel editFlight){
        VuelosModel flight = vuelosRepository.findById(idFlight).get();

        flight.setOrigen(editFlight.getOrigen());
        flight.setDestino(editFlight.getDestino());
        flight.setFechaPartida(editFlight.getFechaPartida());
        flight.setFechaLlegada(editFlight.getFechaLlegada());
        flight.setPrecio(editFlight.getPrecio());
        flight.setAsientos(editFlight.getAsientos());
        flight.setTipoVuelo(editFlight.getTipoVuelo());
        flight.setAerolinea(editFlight.getAerolinea());
        datos = new HashMap<>();
        datos.put("message", "se actualizo con exito");

        vuelosRepository.updateFlight(flight.getIdVuelo(),flight.getOrigen(), flight.getDestino(),flight.getFechaPartida(), flight.getFechaLlegada(), flight.getPrecio(), flight.getAsientos(), flight.getTipoVuelo().getIdTipoVuelo(), flight.getAerolinea().getIdAerolinea());

        return new ResponseEntity<>(
                datos,
                HttpStatus.OK
        );
    }

    public Map<String, Object> getFlightById(Long idVuelo) throws UserNotFoundException {
        VuelosModel vueloEncontrado = this.vuelosRepository.buscarVueloPorId(idVuelo);
        datos = new HashMap<>();

        if(vueloEncontrado!=null){
            datos.put("codigoVuelo", vueloEncontrado.getCodigoVuelo());
            datos.put("origen",vueloEncontrado.getOrigen());
            datos.put("destino", vueloEncontrado.getDestino());
            datos.put("fechaPartida", vueloEncontrado.getFechaPartida());
            datos.put("fechaLlegada", vueloEncontrado.getFechaLlegada());
            datos.put("precio", vueloEncontrado.getPrecio());
            datos.put("asientos", vueloEncontrado.getAsientos());
            datos.put("tipoVuelo", vueloEncontrado.getTipoVuelo().getNombre());
            datos.put("aerolinea", vueloEncontrado.getAerolinea().getNombre());
            return datos;
        }else{
            throw new UserNotFoundException("Vuelo no encontrado");
        }
    }

    public ResponseEntity<Object> saveFlight(VuelosModel vuelo){

        vuelosRepository.insertFlight(vuelo.getOrigen(), vuelo.getDestino(), vuelo.getFechaPartida(), vuelo.getFechaLlegada(), vuelo.getPrecio(), vuelo.getAsientos(), vuelo.getTipoVuelo().getIdTipoVuelo(), vuelo.getAerolinea().getIdAerolinea());

        datos = new HashMap<>();
        datos.put("message", "se guardo con exito");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );

    }

    public ArrayList<VuelosModel> getAllFlightsByWithDate(String origen,  String destino, LocalDate fechaPartida){
        return vuelosRepository.mostrarVuelosPorCriterioConFechas(origen, destino, fechaPartida);
    }

    public ArrayList<VuelosModel> getAllFlightsByWithOutDate(String origen,  String destino){
        return vuelosRepository.mostrarVuelosPorCriterioSinFechas(origen, destino);
    }

    public Boolean deleteFlightById(Long idFlight){
        try {
            vuelosRepository.deleteById(idFlight);
            return true;
        }catch(Exception e){
            return false;
        }
    }


}
