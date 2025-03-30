package com.project.contas.adapters.out;

import com.project.contas.domain.Conta;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ContaRepositoryJpa extends ContaRepository, CrudRepository<Conta, UUID> {
	
    @Query(value = "SELECT new com.project.contas.domain.dto.ContaDTO(o.id, o.dataVencimento, o.dataPagamento, o.descricao, o.situacao, o.valor)"
    		+ " FROM Conta o WHERE o.id = :id")
    ContaDTO buscarContaDTOPorId(UUID id);

    @Query(value = "SELECT new com.project.contas.domain.dto.ContaDTO(o.id, o.dataVencimento, o.dataPagamento, o.descricao, o.situacao, o.valor)"
    		+ " FROM Conta o WHERE (o.dataVencimento BETWEEN :dataVencimentoInicial AND :dataVencimentoFinal) " +
            "AND (:descricao IS NULL OR o.descricao LIKE %:descricao%)")
    List<ContaDTO> listarContasPorFiltro(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable);

    @Query(value = "SELECT SUM(o.valor) FROM Conta o WHERE o.dataPagamento BETWEEN :dataInicial AND :dataFinal AND o.situacao = 3")
    public Double carregarValorPagoPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal);

}
