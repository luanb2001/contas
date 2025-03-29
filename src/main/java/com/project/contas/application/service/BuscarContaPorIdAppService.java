package com.project.contas.application.service;

import com.project.contas.application.usecase.BuscarContaPorIdUseCase;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarContaPorIdAppService implements BuscarContaPorIdUseCase {

    private final ContaRepository contaRepository;

    public BuscarContaPorIdAppService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public ContaDTO executar(UUID id) {
        return this.contaRepository.buscarContaDTOPorId(id);
    }
}
