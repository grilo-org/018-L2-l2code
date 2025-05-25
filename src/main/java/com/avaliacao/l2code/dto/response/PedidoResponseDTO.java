package com.avaliacao.l2code.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PedidoResponseDTO {

    @JsonProperty("pedidos")
    private List<PedidoSaidaDTO> pedidos;
}
