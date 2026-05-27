package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.DeletarFornecedorUseCase;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeletarFornecedorAppService implements DeletarFornecedorUseCase {

    private final FornecedorRepository fornecedorRepository;

    public DeletarFornecedorAppService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void executar(UUID id) {
        fornecedorRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Fornecedor não encontrado"));
        fornecedorRepository.deleteById(id);
    }
}
