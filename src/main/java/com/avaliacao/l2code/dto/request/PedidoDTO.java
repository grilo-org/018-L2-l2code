package com.avaliacao.l2code.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @JsonProperty("pedido_id")
    private int pedidoId;

    @JsonProperty("produtos")
    private List<ProdutoDTO> produtos;
}