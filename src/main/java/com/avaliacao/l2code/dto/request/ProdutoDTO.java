package com.avaliacao.l2code.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    @JsonProperty("produto_id")
    private String produtoId;

    @JsonProperty("dimensoes")
    private DimensoesDTO dimensoes;
}
