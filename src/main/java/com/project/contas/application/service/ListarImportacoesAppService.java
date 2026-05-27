package com.project.contas.application.service;

import com.project.contas.application.usecase.ListarImportacoesUseCase;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarImportacoesAppService implements ListarImportacoesUseCase {

    private final ImportacaoContaRepository importacaoContaRepository;

    public ListarImportacoesAppService(ImportacaoContaRepository importacaoContaRepository) {
        this.importacaoContaRepository = importacaoContaRepository;
    }

    @Override
    public List<ImportacaoContaDTO> executar(Pageable pageable) {
        return importacaoContaRepository.listarImportacoes(pageable);
    }
}
