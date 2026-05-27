package com.project.contas.application.service;

import com.project.contas.application.exception.RegraNegocioException;
import com.project.contas.application.usecase.AtualizarContaUseCase;
import com.project.contas.domain.Fornecedor;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.repository.ContaRepository;
import com.project.contas.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AtualizarContaAppService implements AtualizarContaUseCase {

    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;

    public AtualizarContaAppService(ContaRepository contaRepository, FornecedorRepository fornecedorRepository) {
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void executar(ContaDTO dto) {
        this.contaRepository.findById(dto.id()).ifPresent(conta -> {
            Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedorId())
                    .orElseThrow(() -> new RegraNegocioException("Fornecedor não encontrado"));

            this.contaRepository.save(conta.atualizar(
                    dto.dataVencimento(),
                    dto.dataPagamento(),
                    dto.descricao(),
                    dto.situacaoContaEnum(),
                    dto.valor(),
                    fornecedor
            ));
        });
    }
}
