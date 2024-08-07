package com.nullchefo.userservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nullchefo.userservice.entity.EmailVerificationToken;
import com.nullchefo.userservice.entity.Mail;
import com.nullchefo.userservice.entity.User;
import com.nullchefo.userservice.producer.MailProducer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class MailProducerService {

	@Autowired
	private MailProducer mailProducer;

	@Value("${frontEnd.url}")
	private String frontEndUrl;

	public void sendEmailVerification(User user, String token) {

		Map<String, String> mailFieldsReplacement = new HashMap<>();

		final String templateName = "email_verification";

		String url =
				frontEndUrl
						+ "/user/verifyRegistration/"
						+ token;
		mailFieldsReplacement.put("action_url", url); // TODO make them go out xDD
		mailFieldsReplacement.put("user_first_name", user.getFirstName()); // TODO make them go out xDD
		Mail mail = createMailObject(user, templateName);
		mail.setMailFields(mailFieldsReplacement);


		// TODO use async/ completable feature / use virtual threads
		mailProducer.sendMail(mail);


		log.info("Click the link to email verify: {}",
				 url);

	}

	public String passwordResetTokenMail(User user,  String token) {
		// TODO make the template and make the call to mail-process-service

		final String templateName = "password_reset";

		Map<String, String> mailFieldsReplacement = new HashMap<>();

		String url =
				frontEndUrl
						+ "/user/savePassword/"
						+ token;

		mailFieldsReplacement.put("action_url", url); // TODO make them go out xDD
		mailFieldsReplacement.put("user_first_name", user.getFirstName()); // TODO make them go out xDD

		Mail mail = createMailObject(user,templateName);
		mail.setMailFields(mailFieldsReplacement);

		// TODO use completableFeature
			mailProducer.sendMail(mail);


		log.info("Click the link to Reset your Password: {}",
				 url);
		return url;
	}


	public void resendVerificationTokenMail(User user,  EmailVerificationToken emailVerificationToken) {
		// TODO make the template and make the call to mail-process-service

		String url =
				frontEndUrl
						+ "/verifyRegistration/"
						+ emailVerificationToken.getToken();

		//sendVerificationEmail()
		log.info("Click the link to verify your account: {}",
				 url);

		// TODO when front end receive error expired give choice to resend

	}


	private Mail createMailObject(final User user,final String templateName) {
		Mail mail = new Mail();
		mail.setMailType(templateName); // TODO make them go out xDD
		mail.setRecipient(user.getEmail());
		mail.setUserId(user.getId());
		return mail;
	}

}
