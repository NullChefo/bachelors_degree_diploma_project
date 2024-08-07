package com.nullchefo.mailservice.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nullchefo.mailservice.entity.Mail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailProducer {
	private final String exchange;
	private final String routingKey;
	private final RabbitTemplate rabbitTemplate;

	public MailProducer(
			@Value("${mail.send.exchange}") final String exchange,
			@Value("${mail.routing.key}") final String routingKey,
			final RabbitTemplate rabbitTemplate) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMail(Mail mail) { // sends it to mail-send-service with mail-send-excahnge
		log.trace(String.format("Mail-send-service send message -> %s", mail.toString()));

		rabbitTemplate.convertAndSend(exchange, routingKey, mail);
	}
}
