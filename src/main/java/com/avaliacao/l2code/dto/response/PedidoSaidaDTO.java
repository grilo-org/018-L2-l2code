package com.avaliacao.l2code.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PedidoSaidaDTO {

    @JsonProperty("pedido_id")
    private int pedidoId;

    @JsonProperty("caixas")
    private List<CaixaDTO> caixas;
}
