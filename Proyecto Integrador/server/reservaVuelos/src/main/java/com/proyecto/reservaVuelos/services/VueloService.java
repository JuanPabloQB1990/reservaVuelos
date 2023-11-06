package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.dto.EscalaModelList;
import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VueloService {

    private VueloRepository vueloRepository;

    @Autowired
    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    private HashMap<String, Object> datos;

    public VueloModelList getFlightById(Long idVuelo) throws EntityNotFoundException {
        Optional<VueloModel> vueloEncontrado = this.vueloRepository.findById(idVuelo);
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

    public Stack<ArrayList<VueloModel>> getAllFlightsByWithDate(String origen, String destino, LocalDate fechaPartida) throws EntityNotFoundException {

        List<VueloModel> vuelosEncontrados =  vueloRepository.mostrarVuelosPorCriterioConFecha(origen, destino, fechaPartida);
        Stack<ArrayList<VueloModel>> escalas = new Stack<>();

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
            ;
            escalas.add(new EscalaModelList((ArrayList<VueloModel>) vuelosEncontrados).getVuelos());
            return escalas;
        }else{
            throw new EntityNotFoundException("no hay vuelos para este origen, destino y esta fecha");
        }
    }

    public Stack<ArrayList<VueloModel>> getAllFlightsByWithOutDate(String origen, String destino) throws EntityNotFoundException {

        Stack<ArrayList<VueloModel>> escalas = new Stack<>();
        ArrayList<VueloModel> listaVuelos = new ArrayList<>();
        //buscar vuelos directos
        List<VueloModel> vuelosDirectos = vueloRepository.buscarVuelosDirectosSinFecha(origen, destino);

        if (!vuelosDirectos.isEmpty()){
            escalas.add(new EscalaModelList(listaVuelos).getVuelos());
        }

            // buscar vuelos con solo origen -> primeros vuelos
        List<VueloModel> vuelosSinDestino = vueloRepository.buscarVuelosConSoloOrigen(origen);
        // 1. bogota - medellin 2023-11-06 12:00:02 -- 2023-11-06 13:00:02
        // 2. bogota - medellin 2023-11-06 10:30:02 -- 2023-11-06 11:20:02
        // guardar vuelos con una escala
        for (VueloModel vueloSinDestino: vuelosSinDestino) {
            listaVuelos.clear();
                // buscar vuelos segundo con destino
            List<VueloModel> segundoVueloUnaEscala = vueloRepository.buscarSegundoVueloUnaEscala(vueloSinDestino.getDestino(), destino, vueloSinDestino.getFechaLlegada());
            // 1. medellin - cali - 2023-11-06 15:30:02 -- 2023-11-06 15:30:02
            // 2. medellin - cali - 2023-11-06 14:30:02 -- 2023-11-06 15:20:02
            if (!segundoVueloUnaEscala.isEmpty()){

                listaVuelos.add(vueloSinDestino); // 1. bogota - medellin 2023-11-06 12:00:02 -- 2023-11-06 13:00:02

                for (VueloModel segundoVuelo: segundoVueloUnaEscala) {
                    listaVuelos.add(segundoVuelo); // 1. medellin - cali - 2023-11-06 15:30:02 -- 2023-11-06 15:30:02
                    EscalaModelList escala = new EscalaModelList(listaVuelos);
                    escalas.push(listaVuelos);
                    listaVuelos.remove(1);
                }
            }
        }


        return escalas;

        //buscar vuelos con solo destino - tercer vuelo
        //vuelosConEscalas.clear();
        /*
        for (VueloModel vueloSinDestino: vuelosSinDestino) {
            //bogota - medellin 1:00 pm - 2:00 pm
            //bogota - medellin 9:30 am - 10:30 am
            //bogota - Bucaramanga 9:30 am - 10:30 am
            List<VueloModel> vuelosTerceros = vueloRepository.buscarTercerVueloDosEscalas(destino, vueloSinDestino.getFechaLlegada());
            //pereira - cali 5:00pm - 6:00pm
            //cucuta - cali 5:30 pm - 6:30 pm
            //bucaramanga - cali 3:00 pm - 4:00 pm
            if (!vuelosTerceros.isEmpty()){
                for (VueloModel vueloTercero: vuelosTerceros) {

                    // buscar vuelos intermedios
                    List<VueloModel> vuelosIntermedios = vueloRepository.buscarVuelosIntermidiosDosEscalas(vueloSinDestino.getDestino(),vueloTercero.getOrigen(), vueloSinDestino.getFechaLlegada(), vueloTercero.getFechaPartida());
                    //medellin - pereira 3:00 pm - 4:00
                    //medellin - pereira 3:30 pm - 4:30
                    //bucaramanga - cucuta 3:00 pm - 4:00
                    if (!vuelosIntermedios.isEmpty()){
                        vuelosConEscalas.add(vueloSinDestino); //bogota - Bucaramanga 9:30 am - 10:30 am

                        for (VueloModel vueloIntermedio :vuelosIntermedios) {

                            if (vueloIntermedio.getFechaLlegada().plusHours(1).isBefore(vueloTercero.getFechaPartida())){
                                vuelosConEscalas.add(vueloIntermedio); //medellin - pereira 3:30 pm - 4:30
                                vuelosConEscalas.add(vueloTercero); //pereira - cali 5:00pm - 6:00pm

                                escalas.add(vuelosConEscalas);

                                vuelosConEscalas.pop();
                                vuelosConEscalas.pop();


                            }

                        }
                    }

                }
            }
        }


         */
    }

    public ResponseEntity<Object> updateFlight(Long idVuelo, VueloModel editVuelo) throws EntityNotFoundException {

        Optional<VueloModel> vueloEncontrado = vueloRepository.findById(idVuelo);

        if (vueloEncontrado.isPresent()){

            vueloRepository.actualizarVuelo(
                    idVuelo,
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

    public ResponseEntity<Object> saveFlight(VueloModel vuelo){

        this.vueloRepository.crearVuelo(
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
        Optional<VueloModel> vueloEncontrado = this.vueloRepository.findById(idVuelo);

        if (vueloEncontrado.isPresent()){
            this.vueloRepository.deleteById(idVuelo);
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
    public VueloModel getFlightByCodigo(String codigoVuelo) throws EntityNotFoundException {
        Optional<VueloModel> vueloEncontrado = vueloRepository.findByCodigoVuelo(codigoVuelo);

        if (vueloEncontrado.isPresent()) {
            return vueloEncontrado.get();
        } else {
            throw new EntityNotFoundException("Vuelo no encontrado");
        }
    }

}
