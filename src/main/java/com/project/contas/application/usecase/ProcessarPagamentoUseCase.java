package com.project.contas.application.usecase;

import com.project.contas.domain.dto.PagamentoDTO;

public interface ProcessarPagamentoUseCase {

    void executar(PagamentoDTO pagamentoDTO);
}
