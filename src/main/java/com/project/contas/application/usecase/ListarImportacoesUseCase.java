package com.project.contas.application.usecase;

import com.project.contas.domain.dto.ImportacaoContaDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListarImportacoesUseCase {
    List<ImportacaoContaDTO> executar(Pageable pageable);
}
