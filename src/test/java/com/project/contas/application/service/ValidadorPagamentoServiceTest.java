package com.project.contas.application.service;

import com.project.contas.application.exception.PagamentoInvalidoException;
import com.project.contas.domain.dto.ContaDTO;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.enums.SituacaoContaEnum;
import com.project.contas.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.contas.application.service.ValidadorPagamentoService.*;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorPagamentoServiceTest {

    private ValidadorPagamentoService validadorPagamentoService;

    private UUID contaId;
    private UUID pagamentoId;

    @BeforeEach
    public void setUp() {
        this.validadorPagamentoService = new ValidadorPagamentoService();
        this.contaId = UUID.randomUUID();
        this.pagamentoId = UUID.randomUUID();
    }

    @Test
    public void devePermitirPagamentoValido() {
        ContaDTO contaDTO = new ContaDTO(this.contaId, LocalDateTime.MAX, LocalDateTime.now(),"teste", SituacaoContaEnum.ABERTA,  100.0);
        PagamentoDTO pagamentoDTO = new PagamentoDTO(this.pagamentoId, this.contaId, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        assertDoesNotThrow(() -> this.validadorPagamentoService.validarPagamento(pagamentoDTO, contaDTO));
    }

    @Test
    public void deveLancarExcecaoQuandoContaNaoForEncontrada() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(this.pagamentoId, null, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        PagamentoInvalidoException exception = assertThrows(
                PagamentoInvalidoException.class,
                () -> this.validadorPagamentoService.validarPagamento(pagamentoDTO, null)
        );

        assertEquals(CONTA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaJaFoiPaga() {
        ContaDTO contaDTO = new ContaDTO(this.contaId, LocalDateTime.MAX, LocalDateTime.now(),"teste", SituacaoContaEnum.PAGA,  100.0);
        PagamentoDTO pagamentoDTO = new PagamentoDTO(this.pagamentoId, this.contaId, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        PagamentoInvalidoException exception = assertThrows(
                PagamentoInvalidoException.class,
                () -> this.validadorPagamentoService.validarPagamento(pagamentoDTO, contaDTO)
        );

        assertEquals(CONTA_PAGA, exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaFoiCancelada() {
        ContaDTO contaDTO = new ContaDTO(this.contaId, LocalDateTime.MAX, LocalDateTime.now(),"teste", SituacaoContaEnum.CANCELADA,  100.0);
        PagamentoDTO pagamentoDTO = new PagamentoDTO(this.pagamentoId, this.contaId, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        PagamentoInvalidoException exception = assertThrows(
                PagamentoInvalidoException.class,
                () -> this.validadorPagamentoService.validarPagamento(pagamentoDTO, contaDTO)
        );

        assertEquals(CONTA_CANCELADA, exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoContaEstaVencida() {
        ContaDTO contaDTO = new ContaDTO(this.contaId, LocalDateTime.MAX, LocalDateTime.now(),"teste", SituacaoContaEnum.VENCIDA,  100.0);
        PagamentoDTO pagamentoDTO = new PagamentoDTO(this.pagamentoId, this.contaId, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        PagamentoInvalidoException exception = assertThrows(
                PagamentoInvalidoException.class,
                () -> this.validadorPagamentoService.validarPagamento(pagamentoDTO, contaDTO)
        );

        assertEquals(CONTA_VENCIDA, exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoValorDoPagamentoNaoCoincide() {
        ContaDTO contaDTO = new ContaDTO(this.contaId, LocalDateTime.MAX, LocalDateTime.now(),"teste", SituacaoContaEnum.ABERTA,  99.0);
        PagamentoDTO pagamentoDTO = new PagamentoDTO(this.pagamentoId, this.contaId, LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        PagamentoInvalidoException exception = assertThrows(
                PagamentoInvalidoException.class,
                () -> this.validadorPagamentoService.validarPagamento(pagamentoDTO, contaDTO)
        );

        assertEquals(VALOR_DIVERGENTE, exception.getMessage());
    }
}

