package com.project.contas.domain.dto;

import com.project.contas.domain.enums.SituacaoContaEnum;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AtualizarSituacaoContaDTO(
        @NotNull UUID id,
        @NotNull SituacaoContaEnum situacaoContaEnum
) {}
