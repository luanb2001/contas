package com.project.contas.application.usecase;

import com.project.contas.domain.dto.ImportacaoContaDTO;

import java.util.UUID;

public interface BuscarImportacaoContaUseCase {

    ImportacaoContaDTO executar(UUID id);
}
