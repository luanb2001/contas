package com.project.contas.application.service;

import com.project.contas.adapters.out.messaging.ContaProducer;
import com.project.contas.application.exception.PagamentoInvalidoException;
import com.project.contas.application.usecase.AtualizarSituacaoContaUseCase;
import com.project.contas.application.usecase.BuscarContaPorIdUseCase;
import com.project.contas.application.usecase.ProcessarPagamentoUseCase;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.dto.PagamentoRespostaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.enums.StatusPagamentoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProcessarPagamentoAppService implements ProcessarPagamentoUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ProcessarPagamentoAppService.class);

    private final BuscarContaPorIdUseCase buscarContaPorIdUseCase;
    private final AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase;
    private final ContaProducer contaProducer;
    private final ValidadorPagamentoService validadorPagamentoService;

    public ProcessarPagamentoAppService(AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase, BuscarContaPorIdUseCase buscarContaPorIdUseCase, ContaProducer contaProducer, ValidadorPagamentoService validadorPagamentoService) {
        this.atualizarSituacaoContaUseCase = atualizarSituacaoContaUseCase;
        this.buscarContaPorIdUseCase = buscarContaPorIdUseCase;
        this.contaProducer = contaProducer;
        this.validadorPagamentoService = validadorPagamentoService;
    }

    @Override
    public void executar(PagamentoDTO pagamentoDTO) {
        logger.info("Iniciando processamento do pagamento {}", pagamentoDTO.id());
        ContaDTO contaDTO = buscarContaPorIdUseCase.executar(pagamentoDTO.contaId());

        try {
            this.validadorPagamentoService.validarPagamento(pagamentoDTO, contaDTO);
            this.processarSucesso(pagamentoDTO, contaDTO);
        } catch (PagamentoInvalidoException e) {
            this.processarFalha(pagamentoDTO, e);
        }
    }

    private void processarSucesso(PagamentoDTO pagamentoDTO, ContaDTO contaDTO) {
        logger.info("Pagamento {} processado com sucesso", pagamentoDTO.id());

        this.atualizarSituacaoContaUseCase.executar(new AtualizarSituacaoContaDTO(contaDTO.id(), SituacaoContaEnum.PAGA));
        this.contaProducer.enviarResposta(new PagamentoRespostaDTO(
                pagamentoDTO.id(),
                pagamentoDTO.contaId(),
                LocalDateTime.now(),
                StatusPagamentoEnum.CONFIRMADO,
                "Pagamento efetuado com sucesso"
        ));
    }

    private void processarFalha(PagamentoDTO pagamentoDTO, PagamentoInvalidoException error) {
        logger.warn("Pagamento {} recusado:  - Motivo: {}", pagamentoDTO.id(), error.getMessage());

        this.contaProducer.enviarResposta(new PagamentoRespostaDTO(
                pagamentoDTO.id(),
                pagamentoDTO.contaId(),
                LocalDateTime.now(),
                StatusPagamentoEnum.RECUSADO,
                error.getMessage()
        ));
    }
}
