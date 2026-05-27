package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.CadastrarContaUseCase;
import com.project.contas.domain.Conta;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.CadastrarContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CadastrarContaAppService implements CadastrarContaUseCase {

    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;

    public CadastrarContaAppService(ContaRepository contaRepository, FornecedorRepository fornecedorRepository) {
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void executar(CadastrarContaDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedorId())
                .orElseThrow(() -> new RegraNegocioException("Fornecedor não encontrado"));

        this.contaRepository.save(Conta.cadastrarConta(
                dto.dataVencimento(),
                dto.dataPagamento(),
                dto.descricao(),
                dto.situacaoContaEnum(),
                dto.valor(),
                fornecedor
        ));
    }
}
