package com.project.contas.domain.dto;

import java.time.LocalDateTime;

import com.project.contas.domain.enums.SituacaoContaEnum;

public record CadastrarContaDTO(
        LocalDateTime dataVencimento,
        LocalDateTime dataPagamento,
        String descricao,
        SituacaoContaEnum situacaoContaEnum,
        Double valor
) {}
