package com.project.contas.domain.dto;

import com.project.contas.domain.enums.SituacaoContaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CadastrarContaDTO(
        @NotNull LocalDateTime dataVencimento,
        LocalDateTime dataPagamento,
        @NotBlank String descricao,
        @NotNull SituacaoContaEnum situacaoContaEnum,
        @NotNull @Positive BigDecimal valor,
        @NotNull UUID fornecedorId
) {}
