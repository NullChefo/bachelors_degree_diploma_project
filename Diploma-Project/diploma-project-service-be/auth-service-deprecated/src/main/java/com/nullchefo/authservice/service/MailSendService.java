package com.nullchefo.authservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nullchefo.authservice.entity.Mail;
import com.nullchefo.authservice.entity.User;
import com.nullchefo.authservice.messaging.MailProducer;
import com.nullchefo.authservice.utils.ClientInfo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailSendService {

	@Value("${frontEnd.url}")
	private String productionGatewayAddress;
	@Autowired
	private MailProducer mailProducer;

	public void sendLoginMail(User user, ClientInfo clientInfo) {
		Map<String, String> mailFieldsReplacement = new HashMap<>();

		mailFieldsReplacement.put("user_ip", clientInfo.getClientIpAddr());
		mailFieldsReplacement.put("user_os", clientInfo.getClientOS());
		mailFieldsReplacement.put("user_browser", clientInfo.getClientBrowser());

		mailFieldsReplacement.put("user_first_name", user.getFirstName());

		String formattedDate = getCurrentFormattedDate();

		mailFieldsReplacement.put("login-time", formattedDate);
		mailFieldsReplacement.put("action_url", productionGatewayAddress + "/user/password-reset");

		Mail mail = createMailObject(user);
		mail.setMailFields(mailFieldsReplacement);

		mailProducer.sendMail(mail);

	}

	@NotNull
	private String getCurrentFormattedDate() {
		LocalDateTime currentDate = LocalDateTime.now();
		DateTimeFormatter myFormatObject = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
		return currentDate.format(myFormatObject);
	}

	private Mail createMailObject(final User user) {
		Mail mail = new Mail();
		mail.setMailType("login_mail"); // TODO export in static vars
		mail.setRecipient(user.getEmail());
		mail.setUserId(user.getId());

		return mail;
	}

}
