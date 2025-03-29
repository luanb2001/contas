package com.project.contas.adapters.out.messaging;

import com.project.contas.domain.dto.PagamentoRespostaDTO;
import com.project.contas.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ContaProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ContaProducer contaProducer;

    private static final String CONTA_QUEUE = "conta-queue";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveVerificarMensagemEnviada() {
        PagamentoRespostaDTO pagamentoRespostaDTO = new PagamentoRespostaDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), StatusPagamentoEnum.CONFIRMADO, "teste");

        this.contaProducer.enviarResposta(pagamentoRespostaDTO);

        verify(this.rabbitTemplate, times(1)).convertAndSend(CONTA_QUEUE, pagamentoRespostaDTO);
    }
}
