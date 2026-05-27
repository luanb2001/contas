package com.project.contas.adapters.out;

import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.FornecedorDTO;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FornecedorRepositoryJpa extends FornecedorRepository, CrudRepository<Fornecedor, UUID> {

    @Query("SELECT new com.project.contas.domain.dto.FornecedorDTO(f.id, f.nome) FROM Fornecedor f")
    List<FornecedorDTO> listarFornecedores(Pageable pageable);
}
