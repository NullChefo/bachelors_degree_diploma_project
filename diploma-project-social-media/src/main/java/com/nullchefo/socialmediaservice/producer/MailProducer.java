package com.nullchefo.socialmediaservice.producer;

import com.nullchefo.socialmediaservice.DTO.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailProducer {
    private final String exchange;
    private final String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public MailProducer(
            @Value("${rabbitmq.exchange.name}") final String exchange,
            @Value("${rabbitmq.routing.key}") final String routingKey,
            RabbitTemplate rabbitTemplate) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMail(MailDTO mail) {
        log.trace(String.format("authorization-service send message -> %s", mail.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, mail);
    }
}
