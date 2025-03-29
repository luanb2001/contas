package com.project.contas.application.service;

import com.project.contas.application.exception.PagamentoInvalidoException;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidadorPagamentoService {

    public static final String CONTA_NAO_ENCONTRADA = "Conta não encontrada para o pagamento.";
    public static final String CONTA_PAGA = "Esta conta já foi paga.";
    public static final String CONTA_CANCELADA = "Esta conta foi cancelada.";
    public static final String CONTA_VENCIDA = "Esta conta está vencida e não pode ser paga.";
    public static final String VALOR_DIVERGENTE = "O valor do pagamento não coincide com o valor da conta.";


    public void validarPagamento(PagamentoDTO pagamentoDTO, ContaDTO contaDTO) {
        if (contaDTO == null) {
            throw new PagamentoInvalidoException(CONTA_NAO_ENCONTRADA);
        }

        if (SituacaoContaEnum.PAGA.equals(contaDTO.situacaoContaEnum())) {
            throw new PagamentoInvalidoException(CONTA_PAGA);
        }

        if (SituacaoContaEnum.CANCELADA.equals(contaDTO.situacaoContaEnum())) {
            throw new PagamentoInvalidoException(CONTA_CANCELADA);
        }

        if (SituacaoContaEnum.VENCIDA.equals(contaDTO.situacaoContaEnum())) {
            throw new PagamentoInvalidoException(CONTA_VENCIDA);
        }

        if (!Objects.equals(pagamentoDTO.valor(), contaDTO.valor())) {
            throw new PagamentoInvalidoException(VALOR_DIVERGENTE);
        }
    }
}
