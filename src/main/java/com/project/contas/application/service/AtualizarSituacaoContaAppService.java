package com.project.contas.application.service;

import com.project.contas.application.usecase.AtualizarSituacaoContaUseCase;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
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
    public void executar(AtualizarSituacaoContaDTO atualizarSituacaoContaDTO) {
        this.contaRepository.findById(atualizarSituacaoContaDTO.id()).ifPresent(conta -> {
            if (conta.getSituacao() != null && conta.getSituacao().equals(atualizarSituacaoContaDTO.situacaoContaEnum())) {
                return;
            }

            this.contaRepository.save(conta.atualizarSituacaoConta(atualizarSituacaoContaDTO));
        });
    }
}
