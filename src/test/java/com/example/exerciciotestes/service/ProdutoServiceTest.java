package com.example.exerciciotestes.service;

import com.example.exerciciotestes.model.Produto;
import com.example.exerciciotestes.repository.ProdutoRepository;
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
class ProdutoServiceTest {

    @Mock
    ProdutoRepository clienteRepository;

    @InjectMocks
    ProdutoService produtoService;

    @Test
    void buscaTodosProdutos() {
        List<Produto> clientesMock = List.of(
                new Produto("Produto 1", 50.0)
        );

        when(clienteRepository.findAll()).thenReturn(clientesMock);
        List<Produto> clientesresultado = produtoService.buscaTodosProdutos();
        assertNotNull(clientesresultado);
        assertEquals(clientesMock.get(0).getNomeProduto(), clientesresultado.get(0).getNomeProduto());
        verify(clienteRepository).findAll();
    }

    @Test
    void buscaProdutoPorId() {
        Long produtoId = 1L;
        Produto produto = new Produto("Produto 1", 50.0);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        Produto produtoEncontrado = produtoService.buscaProdutoPorId(produtoId);
        assertNotNull(produtoEncontrado);
        assertEquals(produto.getNomeProduto(), produtoEncontrado.getNomeProduto());
        assertEquals(produto.getValorProduto(), produtoEncontrado.getValorProduto());
        verify(clienteRepository, times(1)).findById(produtoId);
    }
}