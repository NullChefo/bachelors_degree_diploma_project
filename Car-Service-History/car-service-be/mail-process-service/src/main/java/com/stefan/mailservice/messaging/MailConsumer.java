package com.stefan.mailservice.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stefan.mailservice.entity.Mail;
import com.stefan.mailservice.service.MailService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailConsumer {

	@Autowired
	private final MailService mailService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MailConsumer.class);

	@RabbitListener(queues = {"${rabbitmq.queue.name}"})
	public void consumeJsonMessage(Mail mail){
		LOGGER.info(String.format("Received JSON message -> %s", mail.toString()));

		mailService.processAndSend(mail);
	}

}
