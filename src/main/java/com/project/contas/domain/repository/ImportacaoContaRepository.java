package com.project.contas.domain.repository;

import com.project.contas.domain.ImportacaoConta;

import java.util.Optional;
import java.util.UUID;

public interface ImportacaoContaRepository {

    ImportacaoConta save(ImportacaoConta importacaoConta);

    Optional<ImportacaoConta> findById(UUID id);
}
