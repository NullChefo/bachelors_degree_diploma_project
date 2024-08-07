package com.nullchefo.mailservice.service;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.entity.MailDetails;
import com.nullchefo.mailservice.entity.MailStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    private final MailTemplateMakerService mailTemplateMakerService;
    private final JavaMailSender javaMailSender;
    private final MailStatusService mailStatusService;
    private final String sender;

    public MailServiceImpl(
            final MailTemplateMakerService mailTemplateMakerService,
            final JavaMailSender javaMailSender,
            final MailStatusService mailStatusService,
            @Value("${spring.mail.username}") final String sender) {
        this.mailTemplateMakerService = mailTemplateMakerService;
        this.javaMailSender = javaMailSender;
        this.mailStatusService = mailStatusService;
        this.sender = sender;
    }

    // To send a simple email
    public void sendSimpleMail(MailDetails details) {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);

        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error(e.toString());
        }
    }

    // To send an email with attachment
    public void sendMailWithAttachment(MailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender.toString());
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            mimeMessageHelper.setText(details.getMsgBody(), true);
			/*
			// Adding the attachment
			FileSystemResource file
					= new FileSystemResource(
					new File(details.getAttachment()));
			mimeMessageHelper.addAttachment(
					file.getFilename(), file);
			 */

            javaMailSender.send(mimeMessage);

        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public void sendMail(Mail mail) {

        if (mail.getBody() == null || mail.getBody().equals("")) {

            String finalMailBody = null;
            try {
                finalMailBody = mailTemplateMakerService.getFinalEmailTemplate(
                        mail.getMailType(),
                        mail.getMailFields());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mail.setBody(finalMailBody);
        }

        if (mail.getSubject() == null || mail
                .getSubject()
                .equals("")) {
            String title = StringUtils.substringBetween(mail.getBody(), "<title>", "</title>");
            mail.setSubject(title);
        }

        sendMailWithAttachment(new MailDetails(mail));

        // saves the status
        final MailStatus mailStatus = MailStatus.builder().userId(mail.getUserId()).type(mail.getMailType()).build();
        mailStatusService.saveStatus(mailStatus);

    }

}
