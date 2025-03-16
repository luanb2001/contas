package com.project.contas.application.service;

import com.project.contas.adapters.out.messaging.ContaProducer;
import com.project.contas.application.usecase.AtualizarSituacaoContaUseCase;
import com.project.contas.application.usecase.BuscarContaPorIdUseCase;
import com.project.contas.application.usecase.ProcessarPagamentoUseCase;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.dto.PagamentoRespostaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.enums.StatusPagamentoEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ProcessarPagamentoAppService implements ProcessarPagamentoUseCase {

    private final BuscarContaPorIdUseCase buscarContaPorIdUseCase;
    private final AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase;
    private final ContaProducer contaProducer;

    public ProcessarPagamentoAppService(AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase, BuscarContaPorIdUseCase buscarContaPorIdUseCase, ContaProducer contaProducer) {
        this.atualizarSituacaoContaUseCase = atualizarSituacaoContaUseCase;
        this.buscarContaPorIdUseCase = buscarContaPorIdUseCase;
        this.contaProducer = contaProducer;
    }

    @Override
    public void executar(PagamentoDTO pagamentoDTO) {
        ContaDTO contaDTO = this.buscarContaPorIdUseCase.executar(pagamentoDTO.contaId());

        if (Objects.equals(pagamentoDTO.valor(), contaDTO.valor())) {
            this.atualizarSituacaoContaUseCase.executar(new AtualizarSituacaoContaDTO(contaDTO.id(), SituacaoContaEnum.PAGA));
            this.contaProducer.enviarResposta(new PagamentoRespostaDTO(pagamentoDTO.id(), pagamentoDTO.contaId(), LocalDateTime.now(), StatusPagamentoEnum.CONFIRMADO, "Pagamento efetuado com sucesso"));
        } else {
            this.contaProducer.enviarResposta(new PagamentoRespostaDTO(pagamentoDTO.id(), pagamentoDTO.contaId(), LocalDateTime.now(), StatusPagamentoEnum.RECUSADO, "Pagamento não foi efetuado pois o valor não coincide"));
        }
    }
}
