package com.project.contas.adapters.out;

import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FornecedorRepositoryJpa extends FornecedorRepository, CrudRepository<Fornecedor, UUID> {
}
