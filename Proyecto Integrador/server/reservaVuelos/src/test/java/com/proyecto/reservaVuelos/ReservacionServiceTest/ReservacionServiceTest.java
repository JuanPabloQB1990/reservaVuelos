package com.proyecto.reservaVuelos.ReservacionServiceTest;

import com.proyecto.reservaVuelos.dto.CrearReservaDto;
import com.proyecto.reservaVuelos.models.*;
import com.proyecto.reservaVuelos.excepcion.EntityNotFoundException;
import com.proyecto.reservaVuelos.services.ReservacionService;
import com.proyecto.reservaVuelos.repositories.ReservacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservacionServiceTest {

    @Mock
    private ReservacionRepository reservacionRepository;

    @InjectMocks
    private ReservacionService reservacionService;
    private TipoVueloModel tipoVuelo;
    private AerolineaModel aerolinea;

    private ReservacionModel reservacion1;
    private ReservacionModel reservacion2;
    private ReservacionModel reservacion3;

    // Define instancias de vuelo y cliente que se utilizarán en las reservaciones de prueba
    private VueloModel vuelo1;
    private VueloModel vuelo2;
    private VueloModel vuelo3;
    private ClienteModel cliente;
    private SimpleJpaRepository clienteRepository;

    @BeforeEach
    public void SETUP() {
        MockitoAnnotations.initMocks(this);
        tipoVuelo = new TipoVueloModel(1L, "Economica");
        aerolinea = new AerolineaModel(1L, "Avianca");


        ClienteModel cliente1 = new ClienteModel();
        cliente1.setIdCliente(1L);
        cliente1.setNombre("Juan");
        cliente1.setApellido("Pérez");
        cliente1.setTelefono("+123456789");
        cliente1.setCorreo("juan.perez@example.com");
        cliente1.setPais("Colombia");
        cliente1.setCiudad("Bogotá");
        cliente1.setDireccion("Calle 123, Bogotá");

        ClienteModel cliente2 = new ClienteModel();
        cliente2.setIdCliente(2L);
        cliente2.setNombre("Ana");
        cliente2.setApellido("Gómez");
        cliente2.setTelefono("+987654321");
        cliente2.setCorreo("ana.gomez@example.com");
        cliente2.setPais("Colombia");
        cliente2.setCiudad("Medellín");
        cliente2.setDireccion("Carrera 456, Medellín");


        vuelo1 = new VueloModel(
                1L,
                "AV001",
                "Bogotá",
                "Medellín",
                LocalDateTime.of(2023, 11, 9, 9, 0),
                LocalDateTime.of(2023, 11, 9, 10, 0),
                2000,
                100,
                tipoVuelo,
                aerolinea
        );

        vuelo2 = new VueloModel(
                2L,
                "AV002",
                "Medellín",
                "Cali",
                LocalDateTime.of(2023, 11, 10, 12, 0),
                LocalDateTime.of(2023, 11, 10, 13, 0),
                2500,
                120,
                tipoVuelo,
                aerolinea
        );

        vuelo3 = new VueloModel(
                3L,
                "AV003",
                "Bogotá",
                "Cali",
                LocalDateTime.of(2023, 11, 11, 15, 0),
                LocalDateTime.of(2023, 11, 11, 16, 0),
                1800,
                80,
                tipoVuelo,
                aerolinea
        );

        reservacion1 = new ReservacionModel(
                "CódigoReserva1",
                vuelo1,
                vuelo2,
                vuelo3,
                LocalDateTime.of(2023, 11, 15, 10, 0),
                50,
                cliente1
        );

        reservacion2 = new ReservacionModel(
                "CódigoReserva2",
                vuelo2,
                vuelo3,
                vuelo1,
                LocalDateTime.of(2023, 11, 16, 12, 0),
                50,
                cliente2
        );
    }

    @Test
    @DisplayName("crear reservacion exitosa")
    public void testCrearReservacionExitosa() throws EntityNotFoundException {
        // Crear instancia para simular la reserva guardada exitosamente
        ReservacionModel reservacionCreada = new ReservacionModel();
        reservacionCreada.setIdReservacion(1L); // Establecer valores relevantes para la prueba
        reservacionCreada.setCodigoReservacion("ABC123");

        CrearReservaDto crearReservaDto = new CrearReservaDto();
        crearReservaDto.setIdVuelo1(1L);  // ID del vuelo 1
        crearReservaDto.setIdVuelo2(2L);  // ID del vuelo 2
        crearReservaDto.setIdVuelo3(3L);  // ID del vuelo 3
        crearReservaDto.setAsientos(2);   // Número de asientos
        crearReservaDto.setIdCliente(1L); // ID del cliente

        // Configurar comportamiento de reservacionRepository.findById
        when(reservacionRepository.findById(1L)).thenReturn(Optional.of(reservacion1));
        when(reservacionRepository.findById(2L)).thenReturn(Optional.of(reservacion2));

        // Configurar comportamiento de reservacionRepository.save
        when(reservacionRepository.save(any(ReservacionModel.class))).thenReturn(reservacionCreada);

        ResponseEntity<Object> respuesta = reservacionService.crearReservacion(crearReservaDto);

        assertNotNull(respuesta.getBody());
        assertThat(respuesta.getStatusCodeValue()).isEqualTo(200);

        // Verificar que se haya llamado al método save del repositorio una vez
        verify(reservacionRepository, times(1)).save(any(ReservacionModel.class));
    }

    @Test
    @DisplayName("excepcion al crear reservacion con vuelo inexistente")
    public void testCrearReservacionConVueloInexistente() {
        CrearReservaDto crearReservaDto = new CrearReservaDto();
        // Configurar el DTO con datos, pero sin ID de vuelo existente

        when(this.reservacionRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> this.reservacionService.crearReservacion(crearReservaDto));
    }


    @Test
    @DisplayName("cancelar reservación exitosa")
    public void testCancelarReservacionExitosa() throws EntityNotFoundException {
        // Configurar IDs de reservaciones existentes
        Long idReservacion1 = 1L;
        Long idReservacion2 = 2L;

        // Configurar comportamiento de reservacionRepository.findAllById
        when(this.reservacionRepository.findAllById(List.of(idReservacion1, idReservacion2)))
                .thenReturn(List.of(reservacion1, reservacion2));

        // Simular la eliminación de reservaciones existentes
        doNothing().when(reservacionRepository).deleteAllById(List.of(idReservacion1, idReservacion2));

        // Realizar la cancelación y verificar que se haya realizado exitosamente
        ResponseEntity<Object> respuesta = this.reservacionService.cancelarReservacionPorId(idReservacion1, null, idReservacion2);

        assertNotNull(respuesta.getBody());
        assertThat(respuesta.getStatusCodeValue()).isEqualTo(204);
    }



    @Test
    @DisplayName("excepción al cancelar reservación con ID inexistente")
    public void testCancelarReservacionConIdInexistente() {
        Long idReservacion1 = 100L; // ID inexistente
        Long idReservacion2 = 101L; // Otro ID inexistente
        Long idReservacion3 = 102L; // Otro ID inexistente

        // Simulación de no encontrar reservaciones con los IDs proporcionados
        when(reservacionRepository.findAllById(List.of(idReservacion1, idReservacion2, idReservacion3)))
                .thenReturn(List.of());

        // Verificar que arroje una EntityNotFoundException al intentar cancelar
        assertThrows(EntityNotFoundException.class, () -> reservacionService.cancelarReservacionPorId(idReservacion1, idReservacion2, idReservacion3));
    }


    @Test
    @DisplayName("obtener reservaciones por ID de cliente existente")
    public void testObtenerReservacionesPorIdClienteExistente() throws EntityNotFoundException {
        Long idCliente = 1L;
        // Configurar cliente existente en el repositorio
        when(this.clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        List<ReservacionModel> reservacionesCliente = List.of(reservacion1, reservacion2);
        // Configurar comportamiento de reservacionRepository.findByCliente
        when(this.reservacionRepository.findByCliente(cliente)).thenReturn(reservacionesCliente);

        List<ReservacionModel> reservacionesObtenidas = this.reservacionService.obtenerReservacionesPorIdCliente(idCliente);

        assertEquals(reservacionesCliente.size(), reservacionesObtenidas.size());
        // Asegurarse de que las reservaciones obtenidas sean las mismas que las esperadas
        assertThat(reservacionesObtenidas).containsExactlyElementsOf(reservacionesCliente);
    }

    @Test
    @DisplayName("excepción al obtener reservaciones por ID de cliente inexistente")
    public void testObtenerReservacionesPorIdClienteInexistente() {
        Long idCliente = 999L; // ID de cliente inexistente
        // Configurar cliente inexistente en el repositorio
        when(this.clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                this.reservacionService.obtenerReservacionesPorIdCliente(idCliente));
    }

    @Test
    @DisplayName("excepción al obtener reservación con cliente no registrado")
    public void testObtenerReservacionConClienteNoRegistrado() {
        Long idClienteNoRegistrado = 999L; // ID de cliente no registrado
        // Configurar cliente no registrado en el repositorio
        when(this.clienteRepository.findById(idClienteNoRegistrado)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                this.reservacionService.obtenerReservacionesPorIdCliente(idClienteNoRegistrado));
    }

    @Test
    @DisplayName("obtener lista vacía al buscar reservaciones de cliente sin reservas")
    public void testObtenerReservacionesClienteSinReservas() throws EntityNotFoundException {
        Long idClienteSinReservas = 3L; // ID de cliente sin reservas
        // Configurar cliente sin reservas en el repositorio
        when(this.clienteRepository.findById(idClienteSinReservas)).thenReturn(Optional.of(new ClienteModel()));

        List<ReservacionModel> reservacionesObtenidas = this.reservacionService.obtenerReservacionesPorIdCliente(idClienteSinReservas);

        assertTrue(reservacionesObtenidas.isEmpty());
    }

    @Test
    @DisplayName("excepción al actualizar reservación con ID inexistente")
    public void testActualizarReservacionConIdInexistente() {
        Long idReservacionInexistente = 999L;
        // Configurar la búsqueda de reservación con ID inexistente
        when(reservacionRepository.findById(idReservacionInexistente)).thenReturn(Optional.empty());

        ReservacionModel reservacionActualizada = new ReservacionModel();
        reservacionActualizada.setCodigoReservacion("CódigoReservaActualizado");

        // Verificar que arroje una EntityNotFoundException al intentar actualizar una reservación inexistente
        assertThrows(EntityNotFoundException.class, () ->
                reservacionService.actualizarReservacion(idReservacionInexistente, reservacionActualizada.getFechaReservacion()));
    }

    @Test
    @DisplayName("actualización exitosa de reservación")
    public void testActualizarReservacionExitosa() throws EntityNotFoundException {
        Long idReservacionExistente = 1L;
        // Configurar la búsqueda de reservación con ID existente
        when(reservacionRepository.findById(idReservacionExistente)).thenReturn(Optional.of(reservacion1));

        ReservacionModel reservacionActualizada = new ReservacionModel();
        reservacionActualizada.setCodigoReservacion("CódigoReservaActualizado");

        // Simular el guardado de la reservación actualizada
        when(reservacionRepository.save(any())).thenReturn(reservacionActualizada);

        // Realizar la actualización y verificar el resultado
        ReservacionModel resultadoActualizacion = reservacionService.actualizarReservacion(idReservacionExistente, reservacionActualizada.getFechaReservacion());

        assertNotNull(resultadoActualizacion);
        assertEquals(reservacionActualizada.getCodigoReservacion(), resultadoActualizacion.getCodigoReservacion());
    }

}
