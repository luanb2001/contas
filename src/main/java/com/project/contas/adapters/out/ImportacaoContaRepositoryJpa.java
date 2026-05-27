package com.project.contas.adapters.out;

import com.project.contas.domain.ImportacaoConta;
import com.project.contas.domain.dto.ImportacaoContaDTO;
import com.project.contas.domain.repository.ImportacaoContaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImportacaoContaRepositoryJpa extends ImportacaoContaRepository, CrudRepository<ImportacaoConta, UUID> {

    @Query("SELECT new com.project.contas.domain.dto.ImportacaoContaDTO(" +
            "i.id, i.status, i.dataCriacao, i.totalRegistros, i.totalProcessados, i.totalFalhas, i.mensagemErro, i.detalhesErros)" +
            " FROM ImportacaoConta i ORDER BY i.dataCriacao DESC")
    List<ImportacaoContaDTO> listarImportacoes(Pageable pageable);
}
