package com.project.contas.domain.repository;

import com.project.contas.domain.Conta;
import com.project.contas.domain.dto.ContaDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaRepository {

    Conta save(Conta conta);

    Optional<Conta> findById(UUID id);

    ContaDTO buscarContaDTOPorId(UUID id);

    java.math.BigDecimal carregarValorPagoPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<ContaDTO> listarContasPorFiltro(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable);

}
