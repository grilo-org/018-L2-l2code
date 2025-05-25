package com.avaliacao.l2code.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensoesDTO {

    @JsonProperty("altura")
    private int altura;

    @JsonProperty("largura")
    private int largura;

    @JsonProperty("comprimento")
    private int comprimento;
}