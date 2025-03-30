package com.project.contas.application.service;

import com.project.contas.application.usecase.CadastrarContaUseCase;
import com.project.contas.domain.Conta;
import com.project.contas.domain.dto.CadastrarContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CadastrarContaAppService implements CadastrarContaUseCase {

    private final ContaRepository contaRepository;

    public CadastrarContaAppService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
    }

    @Override
    public void executar(CadastrarContaDTO cadastrarContaDTO) {
        this.contaRepository.save(Conta.cadastrarConta(cadastrarContaDTO));
    }
}
