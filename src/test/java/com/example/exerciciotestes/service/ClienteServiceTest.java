package com.example.exerciciotestes.service;

import com.example.exerciciotestes.model.Cliente;
import com.example.exerciciotestes.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    @Test
    void buscaTodosClientes() {
        List<Cliente> clientesMock = List.of(
                new Cliente("Cliente 1", 50.0)
        );

        when(clienteRepository.findAll()).thenReturn(clientesMock);
        List<Cliente> clientesresultado = clienteService.buscaTodosClientes();
        assertNotNull(clientesresultado);
        assertEquals(clientesMock.get(0).getNomeCliente(), clientesresultado.get(0).getNomeCliente());
        verify(clienteRepository).findAll();
    }

    @Test
    void buscaClientePorId() {
               Long clienteId = 1L;
               Cliente cliente = new Cliente("Cliente 1", 50.0);


        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        Cliente clienteEncontrado = clienteService.buscaClientePorId(clienteId);
        assertNotNull(clienteEncontrado);
        assertEquals(cliente.getNomeCliente(), clienteEncontrado.getNomeCliente());
        assertEquals(cliente.getSaldoCliente(), clienteEncontrado.getSaldoCliente());
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    }
