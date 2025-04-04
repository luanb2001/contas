package com.project.contas.application.usecase;

import com.project.contas.domain.dto.ContaDTO;

import java.util.UUID;

public interface BuscarContaPorIdUseCase {

    ContaDTO executar(UUID id);
}
