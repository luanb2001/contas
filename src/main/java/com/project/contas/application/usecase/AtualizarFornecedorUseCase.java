package com.project.contas.application.usecase;

import com.project.contas.domain.dto.CadastrarFornecedorDTO;

import java.util.UUID;

public interface AtualizarFornecedorUseCase {
    void executar(UUID id, CadastrarFornecedorDTO dto);
}
