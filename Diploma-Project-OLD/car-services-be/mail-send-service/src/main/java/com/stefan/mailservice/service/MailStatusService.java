package com.stefan.mailservice.service;

import org.springframework.http.ResponseEntity;

import com.stefan.mailservice.entity.MailStatus;

public interface MailStatusService {
	 void saveStatus(MailStatus mailStatus);

	ResponseEntity<?> removeUser(Long userId);
}
