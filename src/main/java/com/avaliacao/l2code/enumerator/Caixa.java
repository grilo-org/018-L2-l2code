package com.avaliacao.l2code.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
    public enum Caixa {
    CAIXA_1(30, 40, 80),
    CAIXA_2(80, 50, 40),
    CAIXA_3(50, 80, 60);

    private final int altura;
    private final int largura;
    private final int comprimento;

    }
