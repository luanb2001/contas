package com.project.contas.domain.repository;

import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.FornecedorDTO;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FornecedorRepository {

    Fornecedor save(Fornecedor fornecedor);

    Optional<Fornecedor> findById(UUID id);

    List<Fornecedor> findAllByIdIn(Collection<UUID> ids);

    void deleteById(UUID id);

    List<FornecedorDTO> listarFornecedores(Pageable pageable);
}
