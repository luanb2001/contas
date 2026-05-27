package com.project.contas.domain.repository;

import com.project.contas.domain.Fornecedor;

import java.util.Optional;
import java.util.UUID;

public interface FornecedorRepository {

    Fornecedor save(Fornecedor fornecedor);

    Optional<Fornecedor> findById(UUID id);
}
