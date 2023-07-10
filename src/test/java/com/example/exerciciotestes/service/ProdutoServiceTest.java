package com.example.exerciciotestes.service;

import com.example.exerciciotestes.controller.request.ProdutoRequest;
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

    @Test
    void salvarProduto() {
        ProdutoRequest produtoRequest = new ProdutoRequest("Produto 1", 50.0);
        produtoRequest.setNomeProduto("Produto 1");
        produtoRequest.setValorProduto(50.0);

        Produto produtoSalvo = new Produto("Produto 1", 50.0);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        Produto produtoRetornado = produtoService.salvarProduto(produtoRequest);

        assertNotNull(produtoRetornado);
        assertEquals(produtoSalvo.getNomeProduto(), produtoRetornado.getNomeProduto());
        assertEquals(produtoSalvo.getValorProduto(), produtoRetornado.getValorProduto());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void atualizarProduto() {
        Long produtoId = 1L;
        ProdutoRequest produtoRequest = new ProdutoRequest("Produto 1", 50.0);
        produtoRequest.setNomeProduto("Novo Nome");
        produtoRequest.setValorProduto(70.0);

        Produto produtoAtual = new Produto("Produto 2", 70.0);
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoAtual));

        Produto produtoAtualizado = new Produto("Cliente 2", 70.0);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);

        Produto produtoRetornado = produtoService.atualizarProduto(produtoId, produtoRequest);

        assertNotNull(produtoRetornado);
        assertEquals(produtoId, produtoRetornado.getId());
        assertEquals(produtoRequest.getNomeProduto(), produtoRetornado.getNomeProduto());
        assertEquals(produtoRequest.getValorProduto(), produtoRetornado.getValorProduto());
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void detelaProdutoPorId() {
    }
}