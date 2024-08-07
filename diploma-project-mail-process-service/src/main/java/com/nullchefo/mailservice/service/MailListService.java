package com.nullchefo.mailservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nullchefo.mailservice.DTO.UserDTO;
import com.nullchefo.mailservice.entity.MailList;
import com.nullchefo.mailservice.repository.MailListRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailListService {

	private final MailListRepository mailListRepository;

	public MailListService(final MailListRepository mailListRepository) {
		this.mailListRepository = mailListRepository;
	}

	public ResponseEntity<?> registerUser(final UserDTO userDTO) throws Exception {

		MailList mailList = mailListRepository.findByUserId(userDTO.getUserId());
		Long existingRecordId;

		/*
		if (mailList != null){
			{
				existingRecordId = mailList.getId();

			}
		 */

		mailList = new MailList(userDTO);
			/*
			// TODO test
			if (existingRecordId != null){
				mailList.setId(existingRecordId);
			}

			 */

		try {
			mailListRepository.save(mailList);
		} catch (Exception e) {

			// TODO log the exception with proper explain and remove the "e" from the body

			return ResponseEntity.status(409).body(e.toString());

		}
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> removeUser(final Long userId) {

		if (mailListRepository.deleteByUserId(userId)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(410).body("Not Found");
		}

	}

	public ResponseEntity<?> toggleAnnouncements(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);

		if (mailList != null) {
			mailList.setSignedForAnnouncements(!mailList.isSignedForAnnouncements());
			mailListRepository.save(mailList);
		} else {
			return ResponseEntity.status(410).body("Not Found");
		}
		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> togglePromotions(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);
		if (mailList != null) {
			mailList.setSignedForPromotions(!mailList.isSignedForPromotions());
			mailListRepository.save(mailList);
		} else {
			return ResponseEntity.status(410).body("Not Found");
		}
		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> toggleNotifications(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);
		if (mailList != null) {
			mailList.setSignedForNotifications(!mailList.isSignedForNotifications());
			mailListRepository.save(mailList);
		} else {
			return ResponseEntity.status(410).body("Not Found");
		}
		return ResponseEntity.ok().build();
	}

	public List<MailList> getAllUsers() {
		return mailListRepository.findAll();
	}

	public ResponseEntity<MailList> getMailListInfoForUser(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);
		if (mailList == null) {
			return ResponseEntity.status(404).body(null);
		}
		return ResponseEntity.ok(mailList);

	}
}
