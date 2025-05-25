package com.avaliacao.l2code.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CaixaDTO {

    @JsonProperty("caixa_id")
    private String caixaId;

    @JsonProperty("produtos")
    private List<String> produtos;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("observacao")
    private String observacao;
}
