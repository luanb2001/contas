package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.BuscarImportacaoContaUseCase;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarImportacaoContaAppService implements BuscarImportacaoContaUseCase {

    private final ImportacaoContaRepository importacaoContaRepository;

    public BuscarImportacaoContaAppService(ImportacaoContaRepository importacaoContaRepository) {
        this.importacaoContaRepository = importacaoContaRepository;
    }

    @Override
    public ImportacaoContaDTO executar(UUID id) {
        return importacaoContaRepository.findById(id)
                .map(ImportacaoContaDTO::fromEntity)
                .orElseThrow(() -> new RegraNegocioException("Importação não encontrada"));
    }
}
