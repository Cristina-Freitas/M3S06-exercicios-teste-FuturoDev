package com.example.exerciciotestes.service;

import com.example.exerciciotestes.controller.request.ClienteRequest;
import com.example.exerciciotestes.model.Cliente;
import com.example.exerciciotestes.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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

    @Test
    void salvarCliente() {
        ClienteRequest clienteRequest = new ClienteRequest("Cliente 1", 50.0);
        clienteRequest.setNomeCliente("Cliente 1");
        clienteRequest.setSaldoCliente(50.0);

        Cliente clienteSalvo = new Cliente("Cliente 1", 50.0);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        Cliente clienteRetornado = clienteService.salvarCliente(clienteRequest);

        assertNotNull(clienteRetornado);
        assertEquals(clienteSalvo.getNomeCliente(), clienteRetornado.getNomeCliente());
        assertEquals(clienteSalvo.getSaldoCliente(), clienteRetornado.getSaldoCliente());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void atualizarCliente() {
        Long clienteId = 1L;
        ClienteRequest clienteRequest = new ClienteRequest("Cliente 1", 50.0);
        clienteRequest.setNomeCliente("Novo Nome");
        clienteRequest.setSaldoCliente(70.0);

        Cliente clienteAtual = new Cliente("Cliente 2", 50.0);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteAtual));

        Cliente clienteAtualizado = new Cliente("Cliente 2", 70.0);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        Cliente clienteRetornado = clienteService.atualizarCliente(clienteId, clienteRequest);

        assertNotNull(clienteRetornado);
        assertEquals(clienteId, clienteRetornado.getId());
        assertEquals(clienteRequest.getNomeCliente(), clienteRetornado.getNomeCliente());
        assertEquals(clienteRequest.getSaldoCliente(), clienteRetornado.getSaldoCliente());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}


