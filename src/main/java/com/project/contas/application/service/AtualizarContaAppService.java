package com.project.contas.application.service;

import com.project.contas.application.usecase.AtualizarContaUseCase;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AtualizarContaAppService implements AtualizarContaUseCase {

    private ContaRepository contaRepository;

    @Override
    public void executar(ContaDTO contaDTO) {
        this.contaRepository.findById(contaDTO.id()).ifPresent(conta -> this.contaRepository.save(conta.atualizarConta(contaDTO)));
    }

    @Autowired
    public void setContaRepository(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
}
