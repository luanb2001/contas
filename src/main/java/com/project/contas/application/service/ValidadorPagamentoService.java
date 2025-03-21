package com.project.contas.application.service;

import com.project.contas.application.exception.PagamentoInvalidoException;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidadorPagamentoService {

    public void validarPagamento(PagamentoDTO pagamentoDTO, ContaDTO contaDTO) {
        if (contaDTO == null) {
            throw new PagamentoInvalidoException("❌ Conta não encontrada para o pagamento.");
        }

        if (SituacaoContaEnum.PAGA.equals(contaDTO.situacaoContaEnum())) {
            throw new PagamentoInvalidoException("❌ Esta conta já foi paga.");
        }

        if (SituacaoContaEnum.CANCELADA.equals(contaDTO.situacaoContaEnum())) {
            throw new PagamentoInvalidoException("❌ Esta conta foi cancelada.");
        }

        if (SituacaoContaEnum.VENCIDA.equals(contaDTO.situacaoContaEnum())) {
            throw new PagamentoInvalidoException("❌ Esta conta está vencida e não pode ser paga.");
        }

        if (!Objects.equals(pagamentoDTO.valor(), contaDTO.valor())) {
            throw new PagamentoInvalidoException("❌ O valor do pagamento não coincide com o valor da conta.");
        }
    }
}
