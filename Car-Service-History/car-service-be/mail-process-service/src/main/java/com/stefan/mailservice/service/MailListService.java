package com.stefan.mailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.stefan.mailservice.DTO.UserDTO;
import com.stefan.mailservice.entity.MailList;
import com.stefan.mailservice.repository.MailListRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MailListService {

	@Autowired
	private  MailListRepository mailListRepository;


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
		}
		catch (Exception e){

			// TODO log the exception with proper explain and remove the "e" from the body

			return ResponseEntity.status(409).body(e.toString());

		}
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> removeUser(final Long userId) {

		if (mailListRepository.deleteByUserId(userId)){
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.status(410).body("Not Found");
		}

	}

	public ResponseEntity<?> toggleAnnouncements(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);

		if(mailList != null){
			mailList.setSignedForAnnouncements(!mailList.isSignedForAnnouncements());
			mailListRepository.save(mailList);
		}
		else {
			return ResponseEntity.status(410).body("Not Found");
		}
		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> togglePromotions(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);
		if(mailList != null){
			mailList.setSignedForPromotions(!mailList.isSignedForPromotions());
			mailListRepository.save(mailList);
		}
		else {
			return ResponseEntity.status(410).body("Not Found");
		}
		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> toggleNotifications(final Long userId) {

		MailList mailList = mailListRepository.findByUserId(userId);
		if(mailList != null){
			mailList.setSignedForNotifications(!mailList.isSignedForNotifications());
			mailListRepository.save(mailList);
		}
		else {
			return ResponseEntity.status(410).body("Not Found");
		}
		return ResponseEntity.ok().build();
	}
}
