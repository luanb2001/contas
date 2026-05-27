package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.AtualizarFornecedorUseCase;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AtualizarFornecedorAppService implements AtualizarFornecedorUseCase {

    private final FornecedorRepository fornecedorRepository;

    public AtualizarFornecedorAppService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void executar(UUID id, CadastrarFornecedorDTO dto) {
        var fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Fornecedor não encontrado"));
        fornecedor.atualizar(dto.nome());
        fornecedorRepository.save(fornecedor);
    }
}
