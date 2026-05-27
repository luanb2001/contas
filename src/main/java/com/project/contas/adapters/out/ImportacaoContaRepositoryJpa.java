package com.project.contas.adapters.out;

import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImportacaoContaRepositoryJpa extends ImportacaoContaRepository, CrudRepository<ImportacaoConta, UUID> {
}
