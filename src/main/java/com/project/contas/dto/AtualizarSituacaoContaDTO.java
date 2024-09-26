package com.project.contas.dto;

import java.util.UUID;

import com.project.contas.domain.enums.SituacaoContaEnum;

public record AtualizarSituacaoContaDTO(UUID id, SituacaoContaEnum situacaoContaEnum) {
	
}
