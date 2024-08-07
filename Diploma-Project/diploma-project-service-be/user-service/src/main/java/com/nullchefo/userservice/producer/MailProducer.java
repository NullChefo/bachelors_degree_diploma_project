package com.nullchefo.userservice.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nullchefo.userservice.entity.Mail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.routing.key}")
	private String routingKey;

// @Slf4j does this for you
//	private static final Logger LOGGER = LoggerFactory.getLogger(MailProducer.class);

	private final RabbitTemplate rabbitTemplate;

	public MailProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMail(Mail mail) {
		// without @Slf4j LOGGER.info(String.format("user-service send message -> %s", mail.toString()));
		log.info(String.format("user-service send message -> %s", mail.toString()));

		rabbitTemplate.convertAndSend(exchange, routingKey, mail);
	}
}
