package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.DeletarContaUseCase;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeletarContaAppService implements DeletarContaUseCase {

    private final ContaRepository contaRepository;

    public DeletarContaAppService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public void executar(UUID id) {
        contaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Conta não encontrada"));
        contaRepository.deleteById(id);
    }
}
