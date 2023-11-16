package com.proyecto.reservaVuelos.services;

import com.proyecto.reservaVuelos.dto.CrearReservaDto;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.models.VueloModel;
import com.proyecto.reservaVuelos.models.ReservacionModel;
import com.proyecto.reservaVuelos.repositories.ClienteRepository;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;
import com.proyecto.reservaVuelos.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReservacionService {

    private ReservacionRepository reservacionRepository;
    private VueloRepository vueloRepository;
    private ClienteRepository clienteRepository;
    private VueloService vueloService;

    @Autowired
    public ReservacionService(
            ReservacionRepository reservacionRepository,
            VueloRepository vueloRepository,
            ClienteRepository clienteRepository,
            VueloService vueloService
    ) {
        this.reservacionRepository = reservacionRepository;
        this.vueloRepository = vueloRepository;
        this.clienteRepository = clienteRepository;
        this.vueloService = vueloService;

    }

    private HashMap<String, Object> datos;

    List<VueloModel> listaVuelos = new ArrayList<>();

    Optional<ClienteModel> pasajero;

    ReservacionModel reservacion;

    private String generarCodigoReservacion() {
        return UUID.randomUUID().toString();
    }

    public ResponseEntity<Object> crearReservacion(@RequestBody CrearReservaDto reserva) {
        List<Number> listaIdsVuelosReservar = new ArrayList<>();
        try {
            if (reserva.getIdVuelo1() != null && reserva.getIdVuelo2() == null && reserva.getIdVuelo3() == null){
                listaIdsVuelosReservar.add(reserva.getIdVuelo1());

            }
            if (reserva.getIdVuelo1() != null && reserva.getIdVuelo2() != null && reserva.getIdVuelo3() == null) {
                listaIdsVuelosReservar.add(reserva.getIdVuelo1());
                listaIdsVuelosReservar.add(reserva.getIdVuelo2());

            }
            if (reserva.getIdVuelo1() != null && reserva.getIdVuelo2() != null && reserva.getIdVuelo3() != null){
                listaIdsVuelosReservar.add(reserva.getIdVuelo1());
                listaIdsVuelosReservar.add(reserva.getIdVuelo2());
                listaIdsVuelosReservar.add(reserva.getIdVuelo3());

            }

            for (Number id : listaIdsVuelosReservar) {

                // Obtener el vuelo
                Optional<VueloModel> vuelo = vueloRepository.findById((Long) id);

                listaVuelos.add(vuelo.get());

                LocalDateTime fechaSalida = vuelo.get().getFechaPartida();
                //Long numeroAsientosReservar = reservacionModel.getVuelo().getAsientos();

                if (fechaSalida.minusHours(3).isBefore(LocalDateTime.now())) {
                    return ResponseEntity.badRequest().body("La reserva debe realizarse con al menos 3 horas de anticipación.");
                } else {

                    // Asientos disponibles
                    if (vuelo.get().getAsientos() >= reserva.getAsientos()) {
                        // Obtener el pasajero correspondiente
                        pasajero = this.clienteRepository.findById(reserva.getIdCliente());

                        if (pasajero.isPresent()){

                            // Actualizar el número de asientos disponibles
                            vuelo.get().setAsientos(vuelo.get().getAsientos() - reserva.getAsientos());

                            vueloService.actualizarVuelo(vuelo.get().getIdVuelo(), vuelo.get());

                        }else{
                            throw new EntityNotFoundException("el cliente no esta registrado");
                        }


                    } else {
                        return ResponseEntity.badRequest().body("No hay suficientes asientos disponibles para el vuelo "+ vuelo.get().getOrigen() + " " + vuelo.get().getDestino());
                    }
                }
            }
            // Crear y guardar la reserva en la base de datos
            String codigoReservacion = generarCodigoReservacion();

            if (reserva.getIdVuelo1() != null && reserva.getIdVuelo2() == null && reserva.getIdVuelo3() == null){
                reservacion = new ReservacionModel(
                        codigoReservacion,
                        listaVuelos.get(0),
                        LocalDateTime.now(),
                        reserva.getAsientos(),
                        pasajero.get()
                );

            }
            if (reserva.getIdVuelo1() != null && reserva.getIdVuelo2() != null && reserva.getIdVuelo3() == null) {
                reservacion = new ReservacionModel(
                        codigoReservacion,
                        listaVuelos.get(0),
                        listaVuelos.get(1),
                        LocalDateTime.now(),
                        reserva.getAsientos(),
                        pasajero.get()
                );

            }
            if (reserva.getIdVuelo1() != null && reserva.getIdVuelo2() != null && reserva.getIdVuelo3() != null){
                reservacion = new ReservacionModel(
                        codigoReservacion,
                        listaVuelos.get(0),
                        listaVuelos.get(1),
                        listaVuelos.get(2),
                        LocalDateTime.now(),
                        reserva.getAsientos(),
                        pasajero.get()
                );

            return new ResponseEntity<>(datos, HttpStatusCode.valueOf(200));

            }

            reservacionRepository.save(reservacion);

            datos = new HashMap<>();
            datos.put("message", "la reserva se realizo con exito");
            return new ResponseEntity<>(
                    datos,
                    HttpStatusCode.valueOf(200)
            );
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Error al crear la reservación: " + e.getMessage());
        }

    }


    public ResponseEntity<Object> cancelarReservacionPorId(Long idReservacion1,Long idReservacion2,Long idReservacion3) throws EntityNotFoundException {
        List<Long> ids = List.of(idReservacion1,idReservacion2, idReservacion3);
        // Obtener la lista de reservaciones
        List<ReservacionModel> reservaciones = reservacionRepository.findAllById(ids);

        if (!reservaciones.isEmpty()) {
            reservacionRepository.deleteAllById(ids);
            datos = new HashMap<>();
            datos.put("message", "la reserva se realizo con exito");
            return new ResponseEntity<>(
                    datos,
                    HttpStatusCode.valueOf(204)
            );

        } else {
            throw new EntityNotFoundException("No se encontraron reservaciones para eliminar");
        }
    }

    public ReservacionModel actualizarReservacion(Long idReservacion, LocalDateTime nuevaFecha) throws EntityNotFoundException {
        ReservacionModel reservaExistente = reservacionRepository.findById(idReservacion)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con el ID proporcionado: " + idReservacion));

        // Actualizar la fecha de la reserva si se proporciona una nueva fecha
        if (nuevaFecha != null) {
            reservaExistente.setFechaReservacion(nuevaFecha);
        }

        // Guardar la reserva actualizada en la base de datos
        return reservacionRepository.save(reservaExistente);
    }



    public List<ReservacionModel> obtenerReservacionesPorIdCliente(Long idCliente) throws EntityNotFoundException {

        Optional<ClienteModel> cliente = this.clienteRepository.findById(idCliente);

        if (cliente.isPresent()){
            List<ReservacionModel> listaReservaciones = this.reservacionRepository.findByCliente(cliente.get());

            if (!listaReservaciones.isEmpty()){
                return listaReservaciones;
            }

            throw new EntityNotFoundException("El cliente "+ cliente.get().getNombre() + " no tiene reservaciones");
        }
        throw new EntityNotFoundException("El cliente "+ cliente.get().getNombre() + " no esta registrado");


    }

    public ReservacionModel obtenerReservaPorId(Long idReserva) {
        return this.reservacionRepository.findById(idReserva).get();
    }
}
