package com.project.contas.application.usecase;

import com.project.contas.domain.dto.ContaDTO;

public interface AtualizarContaUseCase {

    void executar(ContaDTO contaDTO);
}
