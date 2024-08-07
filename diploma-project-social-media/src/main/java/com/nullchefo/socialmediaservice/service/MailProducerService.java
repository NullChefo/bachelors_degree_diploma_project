package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.MailDTO;
import com.nullchefo.socialmediaservice.entity.EmailVerificationToken;
import com.nullchefo.socialmediaservice.entity.MailList;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.producer.MailProducer;
import com.nullchefo.socialmediaservice.repository.MailListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailProducerService {

    private final MailProducer mailProducer;
    private final String validateRegistrationURL;
    private final String passwordResetURL;

    private final MailListRepository mailListRepository;

    public MailProducerService(
            final MailProducer mailProducer,
            @Value("${address.frontEnd.validateRegistrationURL}") final String validateRegistrationURL,
            @Value("${address.frontEnd.passwordResetURL}") final String passwordResetURL,
            final MailListRepository mailListRepository) {
        this.mailProducer = mailProducer;
        this.validateRegistrationURL = validateRegistrationURL;
        this.passwordResetURL = passwordResetURL;
        this.mailListRepository = mailListRepository;
    }

    public void sendEmailVerification(User user, String token) {

        MailList mailList = mailListRepository.findByUser(user);

        if (mailList == null) {
            mailList = new MailList();
            mailList.setUser(user);
            mailListRepository.save(mailList);
        }

        Map<String, String> mailFieldsReplacement = new HashMap<>();
        final String templateName = "email_verification";

        String url =
                validateRegistrationURL
                        + "/"
                        + token;
        mailFieldsReplacement.put("action_url", url); // TODO make them go out xDD
        mailFieldsReplacement.put("user_first_name", user.getFirstName()); // TODO make them go out xDD
        MailDTO mail = createMailObject(user, templateName);
        mail.setMailFields(mailFieldsReplacement);

        mailProducer.sendMail(mail);

        log.info(
                "Click the link to email verify: {}",
                url);

        if (mailList.getSentMailsForUser() == null) {
            mailList.setSentMailsForUser(1);
        } else {
            mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
        }

        mailListRepository.save(mailList);

    }

    public void passwordResetTokenMail(User user, String token) {
        MailList mailList = mailListRepository.findByUser(user);

        if (mailList == null) {
            mailList = new MailList();
            mailList.setUser(user);
            mailListRepository.save(mailList);
        }

        final String templateName = "password_reset";

        Map<String, String> mailFieldsReplacement = new HashMap<>();

        String url =
                passwordResetURL
                        + "/"
                        + token;

        mailFieldsReplacement.put("action_url", url); // TODO make them go out xDD
        mailFieldsReplacement.put("user_first_name", user.getFirstName()); // TODO make them go out xDD

        MailDTO mail = createMailObject(user, templateName);
        mail.setMailFields(mailFieldsReplacement);

        // TODO use completableFeature
        mailProducer.sendMail(mail);

        log.info(
                "Click the link to Reset your Password: {}",
                url);

        if (mailList.getSentMailsForUser() == null) {
            mailList.setSentMailsForUser(1);
        } else {
            mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
        }
        mailListRepository.save(mailList);

    }

    // TODO check if it will work
    public void resendVerificationTokenMail(User user, EmailVerificationToken emailVerificationToken) {
        MailList mailList = mailListRepository.findByUser(user);

        String url =
                validateRegistrationURL
                        + "/"
                        + emailVerificationToken.getToken();

        //sendVerificationEmail()
        log.info(
                "Click the link to verify your account: {}",
                url);

        if (mailList != null) {
            mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
            mailListRepository.save(mailList);
        }

    }

    private MailDTO createMailObject(final User user, final String templateName) {

        return MailDTO.builder()
                .mailType(templateName)
                .recipient(user.getEmail())
                .userId(user.getId())
                .build();
    }

    //	public void sendLoginMail(Users user, String ipAddress) {
    //
    //		Map<String, String> mailFieldsReplacement = new HashMap<>();
    //		mailFieldsReplacement.put("user_ip", ipAddress);
    //		//		mailFieldsReplacement.put("user_os", clientInfo.getClientOS());
    //		//		mailFieldsReplacement.put("user_browser", clientInfo.getClientBrowser());
    //		mailFieldsReplacement.put("user_first_name", user.getFirstName());
    //		String formattedDate = getCurrentFormattedDate();
    //
    //		mailFieldsReplacement.put("login-time", formattedDate);
    //		mailFieldsReplacement.put("action_url", passwordResetURL);
    //
    //		MailDTO mail = createMailObject(user, "login_mail");
    //		mail.setMailFields(mailFieldsReplacement);
    //
    //		mailProducer.sendMail(mail);
    //	}
    //
    //	@NotNull
    //	private String getCurrentFormattedDate() {
    //		LocalDateTime currentDate = LocalDateTime.now(ZoneOffset.UTC);
    //		DateTimeFormatter myFormatObject = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
    //		return currentDate.format(myFormatObject);
    //	}

}
