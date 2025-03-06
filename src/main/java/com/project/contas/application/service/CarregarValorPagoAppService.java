package com.project.contas.application.service;

import com.project.contas.application.usecase.CarregarValorPagoUseCase;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarregarValorPagoAppService implements CarregarValorPagoUseCase {

    private ContaRepository contaRepository;

    @Override
    public Double executar(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return this.contaRepository.carregarValorPagoPorPeriodo(dataInicial, dataFinal);
    }

    @Autowired
    public void setContaRepository(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
}
