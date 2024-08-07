package com.nullchefo.userservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nullchefo.userservice.entity.EmailVerificationToken;
import com.nullchefo.userservice.entity.Mail;
import com.nullchefo.userservice.entity.User;
import com.nullchefo.userservice.producer.MailProducer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailProducerService {

	private final MailProducer mailProducer;
	private final String validateRegistrationURL;

	private final String passwordResetURL;

	public MailProducerService(
			final MailProducer mailProducer,
			@Value("${frontEnd.validateRegistrationURL}") final String validateRegistrationURL,
			@Value("${frontEnd.passwordResetURL}") final String passwordResetURL) {
		this.mailProducer = mailProducer;
		this.validateRegistrationURL = validateRegistrationURL;
		this.passwordResetURL = passwordResetURL;
	}

	public void sendEmailVerification(User user, String token) {

		Map<String, String> mailFieldsReplacement = new HashMap<>();

		final String templateName = "email_verification";

		String url =
				validateRegistrationURL
						+ "/"
						+ token;
		mailFieldsReplacement.put("action_url", url); // TODO make them go out xDD
		mailFieldsReplacement.put("user_first_name", user.getFirstName()); // TODO make them go out xDD
		Mail mail = createMailObject(user, templateName);
		mail.setMailFields(mailFieldsReplacement);

		// TODO use async/ completable feature / use virtual threads
		mailProducer.sendMail(mail);

		log.info(
				"Click the link to email verify: {}",
				url);

	}

	public String passwordResetTokenMail(User user, String token) {
		// TODO make the template and make the call to mail-process-service

		final String templateName = "password_reset";

		Map<String, String> mailFieldsReplacement = new HashMap<>();

		String url =
				passwordResetURL
						+ "/"
						+ token;

		mailFieldsReplacement.put("action_url", url); // TODO make them go out xDD
		mailFieldsReplacement.put("user_first_name", user.getFirstName()); // TODO make them go out xDD

		Mail mail = createMailObject(user, templateName);
		mail.setMailFields(mailFieldsReplacement);

		// TODO use completableFeature
		mailProducer.sendMail(mail);

		log.info(
				"Click the link to Reset your Password: {}",
				url);
		return url;
	}

	// TODO check if it will work
	public void resendVerificationTokenMail(User user, EmailVerificationToken emailVerificationToken) {
		// TODO make the template and make the call to mail-process-service

		String url =
				validateRegistrationURL
						+ "/"
						+ emailVerificationToken.getToken();

		//sendVerificationEmail()
		log.info(
				"Click the link to verify your account: {}",
				url);

		// TODO when front end receive error expired give choice to resend

	}

	private Mail createMailObject(final User user, final String templateName) {
		Mail mail = new Mail();
		mail.setMailType(templateName); // TODO make them go out xDD
		mail.setRecipient(user.getEmail());
		mail.setUserId(user.getId());
		return mail;
	}

}
