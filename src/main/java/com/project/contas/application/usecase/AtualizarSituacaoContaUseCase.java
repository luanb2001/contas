package com.project.contas.application.usecase;

import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import org.springframework.stereotype.Component;

@Component
public interface AtualizarSituacaoContaUseCase {

    void executar(AtualizarSituacaoContaDTO atualizarSituacaoContaDTO);
}
