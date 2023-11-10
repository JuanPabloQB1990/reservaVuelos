package com.proyecto.reservaVuelos;
import com.proyecto.reservaVuelos.models.ClienteModel;
import com.proyecto.reservaVuelos.repositories.ClienteRepository;
import com.proyecto.reservaVuelos.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        this.clienteRepository = mock(ClienteRepository.class);
        this.clienteService = new ClienteService(this.clienteRepository);
    }

    @Test
    public void testGetPasajeros() {

        ClienteModel cliente1 = new ClienteModel();
        ClienteModel cliente2 = new ClienteModel();
        List<ClienteModel> mockClientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(mockClientes);

        List<ClienteModel> result = clienteService.getPasajeros();


        assertEquals(2, result.size());
        assertEquals(cliente1, result.get(0));
        assertEquals(cliente2, result.get(1));
    }
}
