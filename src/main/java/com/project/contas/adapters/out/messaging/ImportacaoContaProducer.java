package com.project.contas.adapters.out.messaging;

import com.project.contas.domain.dto.ImportarContasCSVMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.project.contas.infrastructure.config.RabbitMQConfig.IMPORTACAO_CONTA_QUEUE;

@Component
public class ImportacaoContaProducer {

    private final RabbitTemplate rabbitTemplate;

    public ImportacaoContaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensagem(ImportarContasCSVMessage mensagem) {
        rabbitTemplate.convertAndSend(IMPORTACAO_CONTA_QUEUE, mensagem);
    }
}
