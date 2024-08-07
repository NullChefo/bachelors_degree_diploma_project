package com.nullchefo.mailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nullchefo.mailservice.entity.MailStatus;
import com.nullchefo.mailservice.repository.MailStatusRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailStatusServiceImpl implements MailStatusService{
	@Autowired
	private MailStatusRepository mailStatusRepository;

	public void saveStatus(final MailStatus mailStatus) {
		mailStatusRepository.save(mailStatus);

	}

	@Override
	public ResponseEntity<?> removeUser(final Long userId) {

		try{
			mailStatusRepository.deleteByUserId(userId);
		}
		catch (Exception e){
			return ResponseEntity.status(409).body("There is a problem removing user from mail status list");
		}
		return ResponseEntity.ok().build();
	}
}
