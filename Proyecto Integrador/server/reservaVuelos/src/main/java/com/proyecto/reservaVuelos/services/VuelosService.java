package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VuelosModel;
import com.proyecto.reservaVuelos.repositories.VuelosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<VuelosModel> getAllFlightsByWithDate(String origen,  String destino, LocalDate fechaPartida, Pageable pageable) throws EntityNotFoundException {

        Page<VuelosModel> vuelosEncontrados =  vuelosRepository.mostrarVuelosPorCriterioConFecha(origen, destino, fechaPartida, pageable);

        if (!vuelosEncontrados.isEmpty()){
            /*
            ArrayList<VueloModelList> listaVuelos = new ArrayList<>();

            for (VuelosModel vuelo: vuelosEncontrados.getContent()) {
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
            */
            return vuelosEncontrados;
        }else{
            throw new EntityNotFoundException("no hay vuelos para este origen, destino y esta fecha");
        }
    }

    public Page<VuelosModel> getAllFlightsByWithOutDate(String origen, String destino, Pageable pageable) throws EntityNotFoundException {

        Page<VuelosModel> vuelosEncontrados = vuelosRepository.mostrarVuelosPorCriterioSinFecha(origen, destino, pageable);

        if (!vuelosEncontrados.isEmpty()){
        /*
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

         */
            return vuelosEncontrados;
        }else{
            throw new EntityNotFoundException("no hay vuelos para este origen y destino");

        }
    }

    public ResponseEntity<Object> updateFlight(Long idVuelo, VuelosModel editVuelo) throws EntityNotFoundException {

        Optional<VuelosModel> vueloEncontrado = vuelosRepository.findById(idVuelo);

        if (vueloEncontrado.isPresent()){

            vuelosRepository.actualizarVuelo(
                    editVuelo.getIdVuelo(),
                    editVuelo.getOrigen(),
                    editVuelo.getDestino(),
                    editVuelo.getFechaPartida(),
                    editVuelo.getFechaLlegada(),
                    editVuelo.getPrecio(),
                    editVuelo.getAsientos(),
                    editVuelo.getTipoVuelo().getIdTipoVuelo(),
                    editVuelo.getAerolinea().getIdAerolinea());

            datos = new HashMap<>();
            datos.put("message", "el vuelo se actualizo con exito");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.OK
            );
        }else{
            throw new EntityNotFoundException("el vuelo no se encuentra registrado");
        }
    }

    public ResponseEntity<Object> saveFlight(VuelosModel vuelo){

        this.vuelosRepository.crearVuelo(
                vuelo.getOrigen(),
                vuelo.getDestino(),
                vuelo.getFechaPartida(),
                vuelo.getFechaLlegada(),
                vuelo.getPrecio(),
                vuelo.getAsientos(),
                vuelo.getTipoVuelo().getIdTipoVuelo(),
                vuelo.getAerolinea().getIdAerolinea());

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

}
