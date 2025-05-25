package com.avaliacao.l2code.service;

import com.avaliacao.l2code.dto.request.DimensoesDTO;
import com.avaliacao.l2code.dto.request.PedidoDTO;
import com.avaliacao.l2code.dto.request.PedidoRequestDTO;
import com.avaliacao.l2code.dto.request.ProdutoDTO;
import com.avaliacao.l2code.dto.response.CaixaDTO;
import com.avaliacao.l2code.dto.response.PedidoResponseDTO;
import com.avaliacao.l2code.dto.response.PedidoSaidaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmpacotarServiceTest {

    private EmpacotarService empacotarService;

    @BeforeEach
    void setup() {
        empacotarService = new EmpacotarService();
    }

    @Test
    void testProcessarPedidos_ComProdutosAlocadosEmCaixas() {
        ProdutoDTO p1 = new ProdutoDTO("PS5", new DimensoesDTO(40, 10, 25));
        ProdutoDTO p2 = new ProdutoDTO("Volante", new DimensoesDTO(40, 30, 30));
        var pedido =  new PedidoDTO(1, List.of(p1, p2));
        var request = new PedidoRequestDTO(List.of(pedido));

        PedidoResponseDTO response = empacotarService.processarPedidos(request);

        assertNotNull(response);
        assertEquals(1, response.getPedidos().size());

        PedidoSaidaDTO pedidoSaida = response.getPedidos().get(0);
        assertEquals(1, pedidoSaida.getPedidoId());
        assertFalse(pedidoSaida.getCaixas().isEmpty());

        CaixaDTO caixa = pedidoSaida.getCaixas().get(0);

        assertNotNull(caixa.getCaixaId());
        assertTrue(caixa.getProdutos().contains("PS5"));
        assertTrue(caixa.getProdutos().contains("Volante"));
        assertNull(caixa.getObservacao());
    }

    @Test
    void testProcessarPedidos_ProdutoNaoCabeEmNenhumaCaixa() {
        ProdutoDTO produtoGigante = new ProdutoDTO("Cadeira Gamer", new DimensoesDTO(120, 60, 70));
        var pedido = new PedidoDTO(5, List.of(produtoGigante));
        var request = new PedidoRequestDTO(List.of(pedido));

        PedidoResponseDTO response = empacotarService.processarPedidos(request);

        PedidoSaidaDTO pedidoSaida = response.getPedidos().get(0);
        assertEquals(5, pedidoSaida.getPedidoId());
        assertEquals(1, pedidoSaida.getCaixas().size());

        CaixaDTO caixa = pedidoSaida.getCaixas().get(0);
        assertNull(caixa.getCaixaId());
        assertEquals(List.of("Cadeira Gamer"), caixa.getProdutos());
        assertEquals("Produto não cabe em nenhuma caixa disponível.", caixa.getObservacao());
    }

    @Test
    void testProcessarPedidos_ThrowsException_WhenRequestIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            empacotarService.processarPedidos(null);
        });
    }

    @Test
    void testProcessarPedidos_ThrowsException_WhenPedidosIsNull() {
        PedidoRequestDTO request = new PedidoRequestDTO(null);
        assertThrows(IllegalArgumentException.class, () -> {
            empacotarService.processarPedidos(request);
        });
    }

    @Test
    void testProcessarPedidos_ComPedidoSemProdutos_RetornaPedidoVazio() {
        var pedidoVazio = new PedidoDTO(10, List.of());
        var request = new PedidoRequestDTO(List.of(pedidoVazio));

        PedidoResponseDTO response = empacotarService.processarPedidos(request);

        PedidoSaidaDTO pedidoSaida = response.getPedidos().get(0);
        assertEquals(10, pedidoSaida.getPedidoId());
        assertTrue(pedidoSaida.getCaixas().isEmpty());
    }
}