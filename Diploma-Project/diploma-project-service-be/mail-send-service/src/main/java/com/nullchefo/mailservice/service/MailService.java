package com.nullchefo.mailservice.service;

import java.io.IOException;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.entity.MailDetails;

public interface MailService {

	// To send a simple email
	boolean sendSimpleMail(MailDetails details);

	// To send an email with attachment
	boolean sendMailWithAttachment(MailDetails details);

	void sendMail(Mail mail) throws IOException;


}
