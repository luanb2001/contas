package com.project.contas.application.service;

import com.project.contas.adapters.out.messaging.ContaProducer;
import com.project.contas.application.exception.PagamentoInvalidoException;
import com.project.contas.application.usecase.AtualizarSituacaoContaUseCase;
import com.project.contas.application.usecase.BuscarContaPorIdUseCase;
import com.project.contas.domain.dto.AtualizarSituacaoContaDTO;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.dto.PagamentoRespostaDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessarPagamentoAppServiceTest {

    @Mock
    private BuscarContaPorIdUseCase buscarContaPorIdUseCase;

    @Mock
    private AtualizarSituacaoContaUseCase atualizarSituacaoContaUseCase;

    @Mock
    private ContaProducer contaProducer;

    @Mock
    private ValidadorPagamentoService validadorPagamentoService;

    @InjectMocks
    private ProcessarPagamentoAppService processarPagamentoAppService;

    private PagamentoDTO pagamentoDTO;
    private ContaDTO contaDTO;

    @BeforeEach
    public void setup() {
        UUID pagamentoId = UUID.randomUUID();
        UUID contaId = UUID.randomUUID();

        this.pagamentoDTO = new PagamentoDTO(pagamentoId, contaId, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);
        this.contaDTO = new ContaDTO(contaId, LocalDateTime.MAX, LocalDateTime.now(),"teste", SituacaoContaEnum.ABERTA,  100.0);
    }

    @Test
    public void deveProcessarPagamentoComSucesso() {
        when(this.buscarContaPorIdUseCase.executar(this.pagamentoDTO.contaId())).thenReturn(this.contaDTO);
        doNothing().when(this.validadorPagamentoService).validarPagamento(this.pagamentoDTO, this.contaDTO);

        this.processarPagamentoAppService.executar(this.pagamentoDTO);

        verify(this.buscarContaPorIdUseCase).executar(this.pagamentoDTO.contaId());
        verify(this.validadorPagamentoService).validarPagamento(this.pagamentoDTO, this.contaDTO);
        verify(this.atualizarSituacaoContaUseCase).executar(new AtualizarSituacaoContaDTO(this.contaDTO.id(), SituacaoContaEnum.PAGA));
        verify(this.contaProducer).enviarResposta(any(PagamentoRespostaDTO.class));
    }

    @Test
    public void deveRejeitarPagamentoQuandoInvalido() {
        when(this.buscarContaPorIdUseCase.executar(this.pagamentoDTO.contaId())).thenReturn(this.contaDTO);
        doThrow(new PagamentoInvalidoException("Erro de validação")).when(this.validadorPagamentoService).validarPagamento(this.pagamentoDTO, this.contaDTO);

        this.processarPagamentoAppService.executar(this.pagamentoDTO);

        verify(this.buscarContaPorIdUseCase).executar(this.pagamentoDTO.contaId());
        verify(this.validadorPagamentoService).validarPagamento(this.pagamentoDTO, this.contaDTO);
        verify(this.atualizarSituacaoContaUseCase, never()).executar(any());
        verify(this.contaProducer).enviarResposta(any(PagamentoRespostaDTO.class));
    }
}
