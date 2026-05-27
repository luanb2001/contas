package com.project.contas.application.usecase;

import com.project.contas.domain.dto.FornecedorDTO;

import java.util.UUID;

public interface BuscarFornecedorPorIdUseCase {

    FornecedorDTO executar(UUID id);
}
