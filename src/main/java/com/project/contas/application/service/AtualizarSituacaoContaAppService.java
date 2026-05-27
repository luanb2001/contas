package com.project.contas.application.service;

import com.project.contas.application.usecase.AtualizarSituacaoContaUseCase;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AtualizarSituacaoContaAppService implements AtualizarSituacaoContaUseCase {

    private final ContaRepository contaRepository;

    public AtualizarSituacaoContaAppService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public void executar(AtualizarSituacaoContaDTO dto) {
        this.contaRepository.findById(dto.id()).ifPresent(conta -> {
            if (SituacaoContaEnum.PAGA.equals(dto.situacaoContaEnum())) {
                conta.pagar();
            } else if (SituacaoContaEnum.CANCELADA.equals(dto.situacaoContaEnum())) {
                conta.cancelar();
            } else {
                conta.setSituacao(dto.situacaoContaEnum());
            }
            this.contaRepository.save(conta);
        });
    }
}
