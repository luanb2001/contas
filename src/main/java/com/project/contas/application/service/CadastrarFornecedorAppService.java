package com.project.contas.application.service;

import com.project.contas.application.usecase.CadastrarFornecedorUseCase;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.CadastrarFornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CadastrarFornecedorAppService implements CadastrarFornecedorUseCase {

    private final FornecedorRepository fornecedorRepository;

    public CadastrarFornecedorAppService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void executar(CadastrarFornecedorDTO cadastrarFornecedorDTO) {
        this.fornecedorRepository.save(Fornecedor.cadastrarFornecedor(cadastrarFornecedorDTO.nome()));
    }
}
