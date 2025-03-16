package com.project.contas.application.service;

import com.project.contas.application.usecase.ListarContasUseCase;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListarContasAppService implements ListarContasUseCase {

    @Autowired
    public ListarContasAppService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    private final ContaRepository contaRepository;

    @Override
    public List<ContaDTO> executar(LocalDateTime dataVencimentoInicial, LocalDateTime dataVencimentoFinal, String descricao, Pageable pageable) {
        return this.contaRepository.listarContasPorFiltro(
                dataVencimentoInicial,
                dataVencimentoFinal,
                descricao,
                pageable
        );
    }
}
