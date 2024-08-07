package com.nullchefo.mailservice.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailConsumer {

	private final MailService mailService;

	public MailConsumer(final MailService mailService) {
		this.mailService = mailService;
	}

	@RabbitListener(queues = { "${rabbitmq.queue.name}" })
	public void consumeJsonMessage(Mail mail) {
		log.trace(String.format("Received JSON message -> %s", mail.toString()));

		mailService.processAndSend(mail);
	}

}
