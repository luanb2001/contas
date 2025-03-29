package com.project.contas.adapters.in.messaging;

import com.project.contas.application.usecase.ProcessarPagamentoUseCase;
import com.project.contas.domain.dto.PagamentoDTO;
import com.project.contas.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PagamentoConsumerTest {

    @Mock
    private ProcessarPagamentoUseCase processarPagamentoUseCase;

    @InjectMocks
    private PagamentoConsumer pagamentoConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveProcessarMensagemQuandoRecebida() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), null, 100.0, StatusPagamentoEnum.PENDENTE, null);

        this.pagamentoConsumer.processarPagamento(pagamentoDTO);

        verify(this.processarPagamentoUseCase, times(1)).executar(pagamentoDTO);
    }
}
