package com.project.contas.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAGAMENTO_QUEUE = "pagamento-queue";
    public static final String CONTA_QUEUE = "conta-queue";
    public static final String EXCHANGE = "exchange";

    @Bean
    public Queue pagamentoQueue() {
        return new Queue(PAGAMENTO_QUEUE, true);
    }

    @Bean
    public Queue contaQueue() {
        return new Queue(CONTA_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding pagamentoBinding(Queue pagamentoQueue, DirectExchange exchange) {
        return BindingBuilder.bind(pagamentoQueue).to(exchange).with(PAGAMENTO_QUEUE);
    }

    @Bean
    public Binding contaBinding(Queue contaQueue, DirectExchange exchange) {
        return BindingBuilder.bind(contaQueue).to(exchange).with(CONTA_QUEUE);
    }
}
