package com.project.contas.domain.dto;

import com.project.contas.domain.enums.SituacaoContaEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContaDTO(
        UUID id,
        LocalDateTime dataVencimento,
        LocalDateTime dataPagamento,
        String descricao,
        SituacaoContaEnum situacaoContaEnum,
        BigDecimal valor,
        UUID fornecedorId,
        String fornecedorNome
) {}
