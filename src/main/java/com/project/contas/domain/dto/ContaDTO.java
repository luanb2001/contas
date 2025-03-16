package com.project.contas.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.contas.domain.enums.SituacaoContaEnum;

public record ContaDTO(
        UUID id,
        LocalDateTime dataVencimento,
        LocalDateTime dataPagamento,
        String descricao,
        SituacaoContaEnum situacaoContaEnum,
        Double valor
) {}
