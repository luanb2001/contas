package com.project.contas.adapters.out.messaging;

import com.project.contas.domain.dto.PagamentoRespostaDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContaProducer {

    private static final String CONTA_QUEUE = "conta-queue";

    private final RabbitTemplate rabbitTemplate;

    public ContaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarResposta(PagamentoRespostaDTO pagamentoRespostaDTO) {
        this.rabbitTemplate.convertAndSend(CONTA_QUEUE, pagamentoRespostaDTO);
        System.out.println("Resposta enviada para a fila: " + pagamentoRespostaDTO);
    }
}


