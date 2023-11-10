package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.dto.EscalaModelList;
import com.proyecto.reservaVuelos.dto.VueloModelList;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.repositories.VueloRepository;
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
public class VueloService {

    private VueloRepository vueloRepository;

    @Autowired
    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    private HashMap<String, Object> datos;

    public VueloModelList obtenerVueloPorId(Long idVuelo) throws EntityNotFoundException {
        Optional<VueloModel> vueloEncontrado = this.vueloRepository.findById(idVuelo);

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

    public Page<VueloModelList> obtenerTodosLosVuelos(Pageable pageable) throws EntityNotFoundException {

        Page<VueloModel> vuelos = this.vueloRepository.findAll(pageable);

        if (vuelos.getTotalElements() > 0){
            Page<VueloModelList> vuelosDto = vuelos
                    .map(vuelo -> VueloModelList.build(
                            vuelo.getCodigoVuelo(),
                            vuelo.getOrigen(),
                            vuelo.getDestino(),
                            vuelo.getFechaPartida(),
                            vuelo.getFechaLlegada(),
                            vuelo.getPrecio(),
                            vuelo.getAsientos(),
                            vuelo.getTipoVuelo().getNombre(),
                            vuelo.getAerolinea().getNombre()));

            return vuelosDto;
        }else{
            throw new EntityNotFoundException("no hay vuelos programados");
        }

    }

    public Stack<List<VueloModelList>> obtenerTodosLosVuelosConFecha(String origen, String destino, LocalDate fechaPartida) throws EntityNotFoundException {

        Stack<List<VueloModelList>> escalas = new Stack<>();

        List<VueloModelList> listaVuelos = new ArrayList<>();

        List<VueloModel> vuelosDirectosConFecha =  vueloRepository.buscarVuelosDirectosConFecha(origen, destino, fechaPartida);

        if (!vuelosDirectosConFecha.isEmpty()){
            for (VueloModel vueloDirecto: vuelosDirectosConFecha) {
                listaVuelos.add(VueloModelList.build(
                        vueloDirecto.getCodigoVuelo(),
                        vueloDirecto.getOrigen(),
                        vueloDirecto.getDestino(),
                        vueloDirecto.getFechaPartida(),
                        vueloDirecto.getFechaLlegada(),
                        vueloDirecto.getPrecio(),
                        vueloDirecto.getAsientos(),
                        vueloDirecto.getTipoVuelo().getNombre(),
                        vueloDirecto.getAerolinea().getNombre()));
            }

            escalas.add(new EscalaModelList(listaVuelos).getVuelos());
        }

        // buscar vuelos con solo origen -> primeros vuelos
        List<VueloModel> primerosVuelos = vueloRepository.buscarVuelosConSoloOrigenConFecha(origen, fechaPartida);
        // 1. bogota - medellin 2023-11-06 12:00:02 -- 2023-11-06 13:00:02
        // 2. bogota - medellin 2023-11-06 10:30:02 -- 2023-11-06 11:20:02
        // guardar vuelos con una escala
        for (VueloModel primerVuelo: primerosVuelos) {

            // buscar vuelos segundo con destino
            List<VueloModel> segundoVueloUnaEscala = vueloRepository.buscarSegundoVueloUnaEscala(primerVuelo.getDestino(), destino, primerVuelo.getFechaLlegada());
            // 1. medellin - cali - 2023-11-06 15:30:02 -- 2023-11-06 15:30:02
            // 2. medellin - cali - 2023-11-06 14:30:02 -- 2023-11-06 15:20:02
            if (!segundoVueloUnaEscala.isEmpty()){

                for (VueloModel segundoVuelo: segundoVueloUnaEscala) {
                    if (segundoVuelo.getFechaPartida().minusHours(1).isAfter(primerVuelo.getFechaLlegada())){

                        listaVuelos.add(VueloModelList.build(
                                primerVuelo.getCodigoVuelo(),
                                primerVuelo.getOrigen(),
                                primerVuelo.getDestino(),
                                primerVuelo.getFechaPartida(),
                                primerVuelo.getFechaLlegada(),
                                primerVuelo.getPrecio(),
                                primerVuelo.getAsientos(),
                                primerVuelo.getTipoVuelo().getNombre(),
                                primerVuelo.getAerolinea().getNombre()));
                        listaVuelos.add(VueloModelList.build(
                                segundoVuelo.getCodigoVuelo(),
                                segundoVuelo.getOrigen(),
                                segundoVuelo.getDestino(),
                                segundoVuelo.getFechaPartida(),
                                segundoVuelo.getFechaLlegada(),
                                segundoVuelo.getPrecio(),
                                segundoVuelo.getAsientos(),
                                segundoVuelo.getTipoVuelo().getNombre(),
                                segundoVuelo.getAerolinea().getNombre()));

                        EscalaModelList escala = new EscalaModelList(listaVuelos);
                        escalas.push(escala.getVuelos());
                        escala.setVuelos(listaVuelos = new ArrayList<>());
                    }

                }
            }

            List<VueloModel> vuelosTerceros = vueloRepository.buscarTercerVueloDosEscalas(destino, primerVuelo.getFechaLlegada());

            if (!vuelosTerceros.isEmpty()) {

                for (VueloModel vueloTercero : vuelosTerceros) {
                    // cucuta - cali - 2023-11-06 19:30:02 -- 2023-11-06 20:20:02
                    // pereira - cali - 2023-11-06 19:20:02 -- 2023-11-06 20:20:02

                    // buscar vuelos intermedios
                    List<VueloModel> vuelosIntermedios = vueloRepository.buscarVuelosIntermidiosDosEscalas(primerVuelo.getDestino(),vueloTercero.getOrigen(), primerVuelo.getFechaLlegada(), vueloTercero.getFechaPartida());
                    //medellin - pereira -- 2023-11-06 15:20:02 -- 2023-11-06 16:20:02
                    //medellin - pereira -- 2023-11-06 16:00:02 -- 2023-11-06 17:00:02
                    if (!vuelosIntermedios.isEmpty()) {

                        for (VueloModel vueloIntermedio :vuelosIntermedios) {

                            if (vueloTercero.getFechaPartida().minusHours(1).isAfter(vueloIntermedio.getFechaLlegada())){
                                listaVuelos.add(VueloModelList.build(
                                        primerVuelo.getCodigoVuelo(),
                                        primerVuelo.getOrigen(),
                                        primerVuelo.getDestino(),
                                        primerVuelo.getFechaPartida(),
                                        primerVuelo.getFechaLlegada(),
                                        primerVuelo.getPrecio(),
                                        primerVuelo.getAsientos(),
                                        primerVuelo.getTipoVuelo().getNombre(),
                                        primerVuelo.getAerolinea().getNombre()));
                                listaVuelos.add(VueloModelList.build(
                                        vueloIntermedio.getCodigoVuelo(),
                                        vueloIntermedio.getOrigen(),
                                        vueloIntermedio.getDestino(),
                                        vueloIntermedio.getFechaPartida(),
                                        vueloIntermedio.getFechaLlegada(),
                                        vueloIntermedio.getPrecio(),
                                        vueloIntermedio.getAsientos(),
                                        vueloIntermedio.getTipoVuelo().getNombre(),
                                        vueloIntermedio.getAerolinea().getNombre()));
                                listaVuelos.add(VueloModelList.build(
                                        vueloTercero.getCodigoVuelo(),
                                        vueloTercero.getOrigen(),
                                        vueloTercero.getDestino(),
                                        vueloTercero.getFechaPartida(),
                                        vueloTercero.getFechaLlegada(),
                                        vueloTercero.getPrecio(),
                                        vueloTercero.getAsientos(),
                                        vueloTercero.getTipoVuelo().getNombre(),
                                        vueloTercero.getAerolinea().getNombre()));

                                EscalaModelList escala = new EscalaModelList(listaVuelos);
                                escalas.push(escala.getVuelos());
                                escala.setVuelos(listaVuelos = new ArrayList<>());
                            }

                        }
                    }

                }
            }
        }

        if (!escalas.isEmpty()){
            return escalas;
        }else {
            throw new EntityNotFoundException("no hay vuelos programados");
        }
    }

    public Stack<List<VueloModelList>> obtenerTodosLosVuelosSinFecha(String origen, String destino) throws EntityNotFoundException {

        Stack<List<VueloModelList>> escalas = new Stack<>();
        ArrayList<VueloModelList> listaVuelos = new ArrayList<>();

        //buscar vuelos directos
        List<VueloModel> vuelosDirectosSinFecha = vueloRepository.buscarVuelosDirectosSinFecha(origen, destino);

        if (!vuelosDirectosSinFecha.isEmpty()){
            for (VueloModel vueloDirecto: vuelosDirectosSinFecha) {
                listaVuelos.add(VueloModelList.build(
                        vueloDirecto.getCodigoVuelo(),
                        vueloDirecto.getOrigen(),
                        vueloDirecto.getDestino(),
                        vueloDirecto.getFechaPartida(),
                        vueloDirecto.getFechaLlegada(),
                        vueloDirecto.getPrecio(),
                        vueloDirecto.getAsientos(),
                        vueloDirecto.getTipoVuelo().getNombre(),
                        vueloDirecto.getAerolinea().getNombre()));

                EscalaModelList escala = new EscalaModelList(listaVuelos);
                escalas.push(escala.getVuelos());
                escala.setVuelos(listaVuelos = new ArrayList<>());
            }

            //escalas.add(new EscalaModelList(listaVuelos).getVuelos());
        }

        // buscar vuelos con solo origen -> primeros vuelos
        List<VueloModel> primerosVuelos = vueloRepository.buscarVuelosConSoloOrigenSinFecha(origen);
        // 1. bogota - medellin 2023-11-06 12:00:02 -- 2023-11-06 13:00:02
        // 2. bogota - medellin 2023-11-06 10:30:02 -- 2023-11-06 11:20:02
        // guardar vuelos con una escala
        for (VueloModel primerVuelo: primerosVuelos) {

            // buscar vuelos segundo con destino
            List<VueloModel> segundoVueloUnaEscala = vueloRepository.buscarSegundoVueloUnaEscala(primerVuelo.getDestino(), destino, primerVuelo.getFechaLlegada());
            // 1. medellin - cali - 2023-11-06 15:30:02 -- 2023-11-06 15:30:02
            // 2. medellin - cali - 2023-11-06 14:30:02 -- 2023-11-06 15:20:02
            if (!segundoVueloUnaEscala.isEmpty()){

                for (VueloModel segundoVuelo: segundoVueloUnaEscala) {
                    if (segundoVuelo.getFechaPartida().minusHours(1).isAfter(primerVuelo.getFechaLlegada())){

                        listaVuelos.add(VueloModelList.build(
                                primerVuelo.getCodigoVuelo(),
                                primerVuelo.getOrigen(),
                                primerVuelo.getDestino(),
                                primerVuelo.getFechaPartida(),
                                primerVuelo.getFechaLlegada(),
                                primerVuelo.getPrecio(),
                                primerVuelo.getAsientos(),
                                primerVuelo.getTipoVuelo().getNombre(),
                                primerVuelo.getAerolinea().getNombre()));
                        listaVuelos.add(VueloModelList.build(
                                segundoVuelo.getCodigoVuelo(),
                                segundoVuelo.getOrigen(),
                                segundoVuelo.getDestino(),
                                segundoVuelo.getFechaPartida(),
                                segundoVuelo.getFechaLlegada(),
                                segundoVuelo.getPrecio(),
                                segundoVuelo.getAsientos(),
                                segundoVuelo.getTipoVuelo().getNombre(),
                                segundoVuelo.getAerolinea().getNombre()));

                        EscalaModelList escala = new EscalaModelList(listaVuelos);
                        escalas.push(escala.getVuelos());
                        escala.setVuelos(listaVuelos = new ArrayList<>());
                    }

                }
            }

            List<VueloModel> vuelosTerceros = vueloRepository.buscarTercerVueloDosEscalas(destino, primerVuelo.getFechaLlegada());

            if (!vuelosTerceros.isEmpty()) {

                for (VueloModel vueloTercero : vuelosTerceros) {
                // cucuta - cali - 2023-11-06 19:30:02 -- 2023-11-06 20:20:02
                // pereira - cali - 2023-11-06 19:20:02 -- 2023-11-06 20:20:02

                // buscar vuelos intermedios
                    List<VueloModel> vuelosIntermedios = vueloRepository.buscarVuelosIntermidiosDosEscalas(primerVuelo.getDestino(),vueloTercero.getOrigen(), primerVuelo.getFechaLlegada(), vueloTercero.getFechaPartida());
                    //medellin - pereira -- 2023-11-06 15:20:02 -- 2023-11-06 16:20:02
                    //medellin - pereira -- 2023-11-06 16:00:02 -- 2023-11-06 17:00:02
                    if (!vuelosIntermedios.isEmpty()) {

                        for (VueloModel vueloIntermedio :vuelosIntermedios) {

                            if (vueloTercero.getFechaPartida().minusHours(1).isAfter(vueloIntermedio.getFechaLlegada()) && vueloIntermedio.getFechaPartida().minusHours(1).isAfter(primerVuelo.getFechaLlegada())){
                                listaVuelos.add(VueloModelList.build(
                                        primerVuelo.getCodigoVuelo(),
                                        primerVuelo.getOrigen(),
                                        primerVuelo.getDestino(),
                                        primerVuelo.getFechaPartida(),
                                        primerVuelo.getFechaLlegada(),
                                        primerVuelo.getPrecio(),
                                        primerVuelo.getAsientos(),
                                        primerVuelo.getTipoVuelo().getNombre(),
                                        primerVuelo.getAerolinea().getNombre()));
                                listaVuelos.add(VueloModelList.build(
                                        vueloIntermedio.getCodigoVuelo(),
                                        vueloIntermedio.getOrigen(),
                                        vueloIntermedio.getDestino(),
                                        vueloIntermedio.getFechaPartida(),
                                        vueloIntermedio.getFechaLlegada(),
                                        vueloIntermedio.getPrecio(),
                                        vueloIntermedio.getAsientos(),
                                        vueloIntermedio.getTipoVuelo().getNombre(),
                                        vueloIntermedio.getAerolinea().getNombre()));
                                listaVuelos.add(VueloModelList.build(
                                        vueloTercero.getCodigoVuelo(),
                                        vueloTercero.getOrigen(),
                                        vueloTercero.getDestino(),
                                        vueloTercero.getFechaPartida(),
                                        vueloTercero.getFechaLlegada(),
                                        vueloTercero.getPrecio(),
                                        vueloTercero.getAsientos(),
                                        vueloTercero.getTipoVuelo().getNombre(),
                                        vueloTercero.getAerolinea().getNombre()));

                                EscalaModelList escala = new EscalaModelList(listaVuelos);
                                escalas.push(escala.getVuelos());
                                escala.setVuelos(listaVuelos = new ArrayList<>());
                            }

                        }
                    }

                }
            }
        }

        if (!escalas.isEmpty()){
            return escalas;
        }else {
            throw new EntityNotFoundException("no hay vuelos programados");
        }

    }

    public ResponseEntity<Object> crearVuelo(VueloModel vuelo){

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

    public ResponseEntity<Object> actualizarVuelo(Long idVuelo, VueloModel editVuelo) throws EntityNotFoundException {

        datos = new HashMap<>();

        Optional<VueloModel> vueloEncontrado = vueloRepository.findById(idVuelo);

        if (vueloEncontrado.isPresent()){
            if (editVuelo.getAerolinea().getIdAerolinea() == vueloEncontrado.get().getAerolinea().getIdAerolinea()){
                vueloEncontrado.get().setCodigoVuelo(editVuelo.getCodigoVuelo());
                vueloEncontrado.get().setOrigen(editVuelo.getOrigen());
                vueloEncontrado.get().setDestino(editVuelo.getDestino());
                vueloEncontrado.get().setFechaPartida(editVuelo.getFechaPartida());
                vueloEncontrado.get().setFechaLlegada(editVuelo.getFechaLlegada());
                vueloEncontrado.get().setPrecio(editVuelo.getPrecio());
                vueloEncontrado.get().setAsientos(editVuelo.getAsientos());
                vueloEncontrado.get().setTipoVuelo(editVuelo.getTipoVuelo());
                vueloEncontrado.get().setAerolinea(editVuelo.getAerolinea());
                VueloModel vuelo = this.vueloRepository.save(vueloEncontrado.get());
            }else{
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
            }

            datos.put("message", "el vuelo se actualizo con exito");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.OK
            );
        }else{
            throw new EntityNotFoundException("el vuelo no se encuentra registrado");
        }
    }

    public ResponseEntity<Object> eliminarVueloPorId(Long idVuelo) throws EntityNotFoundException {
        Optional<VueloModel> vueloEncontrado = this.vueloRepository.findById(idVuelo);

        if (vueloEncontrado.isPresent()) {
            this.vueloRepository.deleteById(idVuelo);
            datos = new HashMap<>();
            datos.put("message", "el vuelo se elimino con exito");
            return new ResponseEntity<>(
                    datos,
                    HttpStatusCode.valueOf(204)
            );
        }else{
            throw new EntityNotFoundException("El vuelo no se encuantra programado");
        }

    }
//    public VueloModel getFlightByCodigo(String codigoVuelo) throws EntityNotFoundException {
//        Optional<VueloModel> vueloEncontrado = vueloRepository.findByCodigoVuelo(codigoVuelo);
//
//        if (vueloEncontrado.isPresent()) {
//            return vueloEncontrado.get();
//        } else {
//            throw new EntityNotFoundException("Vuelo no encontrado");
//        }
//
//    }


}
