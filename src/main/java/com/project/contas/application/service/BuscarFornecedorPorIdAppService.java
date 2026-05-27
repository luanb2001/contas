package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.BuscarFornecedorPorIdUseCase;
import com.project.contas.domain.dto.FornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarFornecedorPorIdAppService implements BuscarFornecedorPorIdUseCase {

    private final FornecedorRepository fornecedorRepository;

    public BuscarFornecedorPorIdAppService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public FornecedorDTO executar(UUID id) {
        return fornecedorRepository.findById(id)
                .map(FornecedorDTO::fromEntity)
                .orElseThrow(() -> new RegraNegocioException("Fornecedor não encontrado"));
    }
}
