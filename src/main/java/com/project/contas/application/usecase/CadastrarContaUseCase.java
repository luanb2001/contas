package com.project.contas.application.usecase;

import com.project.contas.domain.dto.CadastrarContaDTO;

public interface CadastrarContaUseCase {

    void executar(CadastrarContaDTO cadastrarContaDTO);
}
