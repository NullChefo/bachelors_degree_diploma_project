package com.stefan.authservice.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stefan.authservice.entity.Mail;

@Service
public class MailProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailProducer.class);
	private final RabbitTemplate rabbitTemplate;
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	public MailProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	public void sendMail(Mail mail) {
		LOGGER.info(String.format("Auth-service send message -> %s", mail.toString()));
		rabbitTemplate.convertAndSend(exchange, routingKey, mail);
	}
}
