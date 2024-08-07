package com.nullchefo.mailservice.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.entity.MailDetails;
import com.nullchefo.mailservice.entity.MailStatus;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailServiceImpl implements MailService {


	@Autowired
	private MailTemplateMakerService mailTemplateMakerService;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private MailStatusService mailStatusService;

	@Value("${spring.mail.username}")
	private String sender;




	// To send a simple email
	public boolean sendSimpleMail(MailDetails details) {

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
			return true;
		}



		// Catch block to handle the exceptions
		catch (Exception e) {
			return false;
		}
	}

	// To send an email with attachment
	public boolean sendMailWithAttachment(MailDetails details) {
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


			mimeMessageHelper.setText(details.getMsgBody() , true);
			/*
			// Adding the attachment
			FileSystemResource file
					= new FileSystemResource(
					new File(details.getAttachment()));
			mimeMessageHelper.addAttachment(
					file.getFilename(), file);
			 */

				// TODO use async/ completable feature / use virtual threads
				javaMailSender.send(mimeMessage);

			return true;
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return false;
		}
	}




	// TODO make it async or use virtual threads
	public void sendMail(Mail mail) throws IOException {


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


			if (mail.getSubject() == null || mail.getSubject().equals("")) { // TODO async and replace StringUtils.substringBetween with own code
				String title = StringUtils.substringBetween(mail.getBody(), "<title>", "</title>");
				mail.setSubject(title);
			}

		// TODO use async/ completable feature / use virtual threads
			boolean sendCorrectly = sendMailWithAttachment(new MailDetails(mail));
			final MailStatus mailStatus = new MailStatus(mail.getUserId(), mail.getMailType(), sendCorrectly); // TODO async
			mailStatusService.saveStatus(mailStatus);


	}



}
