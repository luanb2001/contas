package com.project.contas.domain.repository;

import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImportacaoContaRepository {

    ImportacaoConta save(ImportacaoConta importacaoConta);

    Optional<ImportacaoConta> findById(UUID id);

    List<ImportacaoContaDTO> listarImportacoes(Pageable pageable);
}
