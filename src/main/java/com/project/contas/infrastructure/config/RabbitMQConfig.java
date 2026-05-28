package com.project.contas.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String IMPORTACAO_CONTA_QUEUE = "importacao-conta-queue";
    public static final String IMPORTACAO_CONTA_DLQ = "importacao-conta-dlq";
    public static final String EXCHANGE = "exchange";

    @Bean
    public Queue importacaoContaQueue() {
        return QueueBuilder.durable(IMPORTACAO_CONTA_QUEUE)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", IMPORTACAO_CONTA_DLQ)
                .build();
    }

    @Bean
    public Queue importacaoContaDlqQueue() {
        return new Queue(IMPORTACAO_CONTA_DLQ, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding importacaoContaBinding(Queue importacaoContaQueue, DirectExchange exchange) {
        return BindingBuilder.bind(importacaoContaQueue).to(exchange).with(IMPORTACAO_CONTA_QUEUE);
    }

    @Bean
    public Binding importacaoContaDlqBinding(Queue importacaoContaDlqQueue, DirectExchange exchange) {
        return BindingBuilder.bind(importacaoContaDlqQueue).to(exchange).with(IMPORTACAO_CONTA_DLQ);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}