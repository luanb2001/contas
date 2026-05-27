package com.project.contas.application.service;

import com.project.contas.application.usecase.ListarFornecedoresUseCase;
import com.project.contas.domain.dto.FornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarFornecedoresAppService implements ListarFornecedoresUseCase {

    private final FornecedorRepository fornecedorRepository;

    public ListarFornecedoresAppService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public List<FornecedorDTO> executar(Pageable pageable) {
        return fornecedorRepository.listarFornecedores(pageable);
    }
}
