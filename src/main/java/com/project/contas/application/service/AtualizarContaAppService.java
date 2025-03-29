package com.project.contas.application.service;

import com.project.contas.application.usecase.AtualizarContaUseCase;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AtualizarContaAppService implements AtualizarContaUseCase {

    private final ContaRepository contaRepository;

    public AtualizarContaAppService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public void executar(ContaDTO contaDTO) {
        this.contaRepository.findById(contaDTO.id()).ifPresent(conta -> this.contaRepository.save(conta.atualizarConta(contaDTO)));
    }
}
