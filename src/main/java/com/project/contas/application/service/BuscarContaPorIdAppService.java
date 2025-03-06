package com.project.contas.application.service;

import com.project.contas.application.usecase.BuscarContaPorIdUseCase;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.dto.ContaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarContaPorIdAppService implements BuscarContaPorIdUseCase {

    private ContaRepository contaRepository;

    @Override
    public ContaDTO executar(UUID id) {
        return this.contaRepository.buscarContaDTOPorId(id);
    }

    @Autowired
    public void setContaRepository(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
}
