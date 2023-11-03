package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VuelosModel;
import com.proyecto.reservaVuelos.repositories.VuelosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VuelosService {

    private VuelosRepository vuelosRepository;

    @Autowired
    public VuelosService(VuelosRepository vuelosRepository) {
        this.vuelosRepository = vuelosRepository;
    }

    private HashMap<String, Object> datos;

    public VueloModelList getFlightById(Long idVuelo) throws EntityNotFoundException {
        Optional<VuelosModel> vueloEncontrado = this.vuelosRepository.findById(idVuelo);
        datos = new HashMap<>();

        if(vueloEncontrado.isPresent()){
            return VueloModelList.build(
                    vueloEncontrado.get().getCodigoVuelo(),
                    vueloEncontrado.get().getOrigen(),
                    vueloEncontrado.get().getDestino(),
                    vueloEncontrado.get().getFechaPartida(),
                    vueloEncontrado.get().getFechaLlegada(),
                    vueloEncontrado.get().getPrecio(),
                    vueloEncontrado.get().getAsientos(),
                    vueloEncontrado.get().getTipoVuelo().getNombre(),
                    vueloEncontrado.get().getAerolinea().getNombre()
            );
        }else{
            throw new EntityNotFoundException("Vuelo no encontrado");
        }
    }

    public ArrayList<VueloModelList> getAllFlightsByWithDate(String origen,  String destino, LocalDate fechaPartida) throws EntityNotFoundException {

        ArrayList<VuelosModel> vuelosEncontrados =  vuelosRepository.mostrarVuelosPorCriterioConFecha(origen, destino, fechaPartida);

        if (!vuelosEncontrados.isEmpty()){

            ArrayList<VueloModelList> listaVuelos = new ArrayList<>();

            for (VuelosModel vuelo: vuelosEncontrados) {
             listaVuelos.add(VueloModelList.build(
                     vuelo.getCodigoVuelo(),
                     vuelo.getOrigen(),
                     vuelo.getDestino(),
                     vuelo.getFechaPartida(),
                     vuelo.getFechaLlegada(),
                     vuelo.getPrecio(),
                     vuelo.getAsientos(),
                     vuelo.getTipoVuelo().getNombre(),
                     vuelo.getAerolinea().getNombre()));
            }
            return listaVuelos;
        }else{
            throw new EntityNotFoundException("no hay vuelos para este origen, destino y esta fecha");
        }
    }

    public ArrayList<VueloModelList> getAllFlightsByWithOutDate(String origen,  String destino) throws EntityNotFoundException {

        ArrayList<VuelosModel> vuelosEncontrados =  vuelosRepository.mostrarVuelosPorCriterioSinFecha(origen, destino);

        if (!vuelosEncontrados.isEmpty()){

            ArrayList<VueloModelList> listaVuelos = new ArrayList<>();

            for (VuelosModel vuelo: vuelosEncontrados) {
                listaVuelos.add(VueloModelList.build(
                        vuelo.getCodigoVuelo(),
                        vuelo.getOrigen(),
                        vuelo.getDestino(),
                        vuelo.getFechaPartida(),
                        vuelo.getFechaLlegada(),
                        vuelo.getPrecio(),
                        vuelo.getAsientos(),
                        vuelo.getTipoVuelo().getNombre(),
                        vuelo.getAerolinea().getNombre()));
            }
            return listaVuelos;
        }else{
            throw new EntityNotFoundException("no hay vuelos para este origen y destino");

        }
    }

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
        datos.put("message", "el vuelo se actualizo con exito");

        vuelosRepository.updateFlight(flight.getIdVuelo(),flight.getOrigen(), flight.getDestino(),flight.getFechaPartida(), flight.getFechaLlegada(), flight.getPrecio(), flight.getAsientos(), flight.getTipoVuelo().getIdTipoVuelo(), flight.getAerolinea().getIdAerolinea());

        return new ResponseEntity<>(
                datos,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> saveFlight(VuelosModel vuelo){

        this.vuelosRepository.crearVuelo(vuelo.getOrigen(), vuelo.getDestino(), vuelo.getFechaPartida(), vuelo.getFechaLlegada(), vuelo.getPrecio(), vuelo.getAsientos(), vuelo.getTipoVuelo().getIdTipoVuelo(), vuelo.getAerolinea().getIdAerolinea());

        datos = new HashMap<>();
        datos.put("message", "el vuelo se guardo con exito");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deleteFlightById(Long idVuelo) throws EntityNotFoundException {
        Optional<VuelosModel> vueloEncontrado = this.vuelosRepository.findById(idVuelo);

        if (vueloEncontrado.isPresent()){
            this.vuelosRepository.deleteById(idVuelo);
            datos = new HashMap<>();
            datos.put("message", "el vuelo se elimino con exito");
            return new ResponseEntity<>(
                    datos,
                    HttpStatusCode.valueOf(200)
            );
        }else{
            throw new EntityNotFoundException("El vuelo no se encuantra programado");
        }

    }
    public VuelosModel getFlightByCodigo(String codigoVuelo) throws EntityNotFoundException {
        Optional<VuelosModel> vueloEncontrado = vuelosRepository.findByCodigoVuelo(codigoVuelo);

        if (vueloEncontrado.isPresent()) {
            return vueloEncontrado.get();
        } else {
            throw new EntityNotFoundException("Vuelo no encontrado");
        }
    }

}
