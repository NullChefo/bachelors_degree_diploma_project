package com.nullchefo.mailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.entity.MailList;
import com.nullchefo.mailservice.messaging.MailConsumer;
import com.nullchefo.mailservice.messaging.MailProducer;
import com.nullchefo.mailservice.repository.MailListRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailService {

	@Autowired
	private MailListRepository mailListRepository;
	@Autowired
	private MailProducer mailProducer;
	private static final Logger LOGGER = LoggerFactory.getLogger(MailConsumer.class);

	public void processAndSend(final Mail mail) {
		MailList mailList = mailListRepository.findByUserId(mail.getUserId());

		if (mailList == null) {

			mailList = new MailList();
			mailList.setUserId(mail.getUserId());
			mailList.setSignedForPromotions(false);
			mailList.setSignedForAnnouncements(false);
			mailList.setSignedForNotifications(false);

		}

		if (isUserSignedForEmail(mail, mailList)) {
			return;
		}
		mailList.setSentMailsForUser(mailList.getSentMailsForUser() + 1);
		// TODO Review Later
		final MailList newMailList = mailList;

		// TODO use async
			// code to run in thread
			mailListRepository.save(newMailList);


		// TODO use async
			// code to run in thread
			mailProducer.sendMail(mail);



	}

	private boolean isUserSignedForEmail(final Mail mail, final MailList mailList) {

		//  TODO get that out
		if (mail.getMailType().equalsIgnoreCase("announcements")  && mailList.isSignedForAnnouncements() ){

			LOGGER.info(String.format("user: %s not sign up for announcements" , mailList.getUserId()));
			return true;
		}
		//  TODO get that out
		if (mail.getMailType().equalsIgnoreCase("promotions")  && mailList.isSignedForPromotions() ){

			LOGGER.info(String.format("user: %s not sign up for promotions" , mailList.getUserId()));
			return true;
		}
		//  TODO get that out
		if (mail.getMailType().equalsIgnoreCase("notifications")  && mailList.isSignedForNotifications() ){

			LOGGER.info(String.format("user: %s not sign up for notifications" , mailList.getUserId()));
			return true;
		}
		return false;
	}



}
