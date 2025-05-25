package com.avaliacao.l2code.service;
import com.avaliacao.l2code.dto.request.PedidoRequestDTO;
import com.avaliacao.l2code.dto.request.ProdutoDTO;
import com.avaliacao.l2code.dto.response.CaixaDTO;
import com.avaliacao.l2code.dto.response.PedidoResponseDTO;
import com.avaliacao.l2code.dto.response.PedidoSaidaDTO;
import com.avaliacao.l2code.enumerator.Caixa;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class EmpacotarService {

    public PedidoResponseDTO processarPedidos(PedidoRequestDTO request) {
        if (request == null || request.getPedidos() == null) {
            throw new IllegalArgumentException("O pedido ou a lista de pedidos não pode ser nula.");
        }

        var pedidos = request.getPedidos().stream().filter(Objects::nonNull)
                .map(pedido -> {
                    if (pedido.getProdutos() == null || pedido.getProdutos().isEmpty()) {
                        return new PedidoSaidaDTO(pedido.getPedidoId(), List.of());
                    }
                    var caixasUsadas = alocarProdutosEmCaixas(pedido.getProdutos());
                    return new PedidoSaidaDTO(pedido.getPedidoId(), caixasUsadas);
                })
                .toList();

        return new PedidoResponseDTO(pedidos);
    }

    private List<CaixaDTO> alocarProdutosEmCaixas(List<ProdutoDTO> produtos) {
        var caixasUsadas = new ArrayList<CaixaDTO>();
        var produtosRestantes = new ArrayList<>(produtos);

        var caixasOrdenadas = Arrays.stream(Caixa.values())
                .sorted(Comparator.comparingLong(c -> (long) c.getAltura() * c.getLargura() * c.getComprimento()))
                .toList();

        while (!produtosRestantes.isEmpty()) {
            var produtoAlocado = false;

            for (var caixaEnum : caixasOrdenadas) {
                var encaixam = selecionarProdutosParaCaixa(produtosRestantes, caixaEnum);
                if (!encaixam.isEmpty()) {
                    caixasUsadas.add(new CaixaDTO(
                            caixaEnum.name(),
                            encaixam.stream().map(ProdutoDTO::getProdutoId).toList(),
                            null
                    ));
                    produtosRestantes.removeAll(encaixam);
                    produtoAlocado = true;
                    break;
                }
            }

            if (!produtoAlocado) {
                var produtoNaoEncaixa = produtosRestantes.remove(0);
                caixasUsadas.add(new CaixaDTO(
                        null,
                        List.of(produtoNaoEncaixa.getProdutoId()),
                        "Produto não cabe em nenhuma caixa disponível."
                ));
            }
        }
        return caixasUsadas;
    }

    private List<ProdutoDTO> selecionarProdutosParaCaixa(List<ProdutoDTO> produtos, Caixa caixaEnum) {
        var encaixam = new ArrayList<ProdutoDTO>();
        long volumeCaixa = (long) caixaEnum.getAltura() * caixaEnum.getLargura() * caixaEnum.getComprimento();
        long volumeUsado = 0;

        var produtosOrdenados = produtos.stream()
                .sorted(Comparator.comparingInt(p -> p.getDimensoes().getAltura()))
                .toList();

        for (var produto : produtosOrdenados) {
            long volumeProduto = (long) produto.getDimensoes().getAltura()
                    * produto.getDimensoes().getLargura()
                    * produto.getDimensoes().getComprimento();

            if (volumeUsado + volumeProduto <= volumeCaixa
                    && produto.getDimensoes().getAltura() <= caixaEnum.getAltura()
                    && produto.getDimensoes().getLargura() <= caixaEnum.getLargura()
                    && produto.getDimensoes().getComprimento() <= caixaEnum.getComprimento()) {

                encaixam.add(produto);
                volumeUsado += volumeProduto;
            }
        }

        return encaixam;
    }
}