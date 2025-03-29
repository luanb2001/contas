package com.project.contas.adapters.in.messaging;

import com.project.contas.application.usecase.ProcessarPagamentoUseCase;
import com.project.contas.domain.dto.PagamentoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.project.contas.infrastructure.config.RabbitMQConfig.PAGAMENTO_QUEUE;

@Component
public class PagamentoConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PagamentoConsumer.class);

    private final ProcessarPagamentoUseCase processarPagamentoUseCase;

    public PagamentoConsumer(ProcessarPagamentoUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    @RabbitListener(queues = PAGAMENTO_QUEUE)
    public void processarPagamento(PagamentoDTO pagamentoDTO) {
        PagamentoConsumer.logger.info("Mensagem recebida no Projeto Contas: {}", pagamentoDTO);
        this.processarPagamentoUseCase.executar(pagamentoDTO);
    }
}
