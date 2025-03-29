package com.project.contas.application.service;

import com.project.contas.application.usecase.CadastrarContaUseCase;
import com.project.contas.domain.Conta;
import com.project.contas.domain.dto.CadastrarContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CadastrarContaAppService implements CadastrarContaUseCase {

    private final ContaRepository contaRepository;

    public CadastrarContaAppService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
    }

    @Override
    public void executar(CadastrarContaDTO cadastrarContaDTO) {
        Conta conta = new Conta();
        conta.setId(UUID.randomUUID());
        conta.setDataVencimento(cadastrarContaDTO.dataVencimento());
        conta.setDataPagamento(cadastrarContaDTO.dataPagamento());
        conta.setDescricao(cadastrarContaDTO.descricao());
        conta.setSituacao(cadastrarContaDTO.situacaoContaEnum());
        conta.setValor(cadastrarContaDTO.valor());

        this.contaRepository.save(conta);
    }
}
