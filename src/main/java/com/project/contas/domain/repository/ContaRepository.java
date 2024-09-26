package com.project.contas.domain.repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.project.contas.domain.Conta;
import com.project.contas.dto.ContaDTO;

public interface ContaRepository {

    public Conta save(Conta conta);

    public Optional<Conta> findById(UUID id);
    
    public ContaDTO buscarContaDTOPorId(UUID id);

    public Double carregarValorPagoPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal);

    public List<ContaDTO> listarContasPorFiltro(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable);

}
