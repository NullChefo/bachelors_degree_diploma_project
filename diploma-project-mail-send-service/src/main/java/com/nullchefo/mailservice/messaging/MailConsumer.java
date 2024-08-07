package com.nullchefo.mailservice.messaging;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MailConsumer {

    private final MailService mailService;

    public MailConsumer(final MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeJsonMessage(Mail mail) throws IOException {
        log.trace(String.format("Received JSON message -> %s", mail.toString()));

        //	Thread.ofVirtual().start(() -> {
        try {
            mailService.sendMail(mail);
        } catch (Exception e) {
            log.error("Error while sending mail" + e);
        }
        //	});

    }

}
