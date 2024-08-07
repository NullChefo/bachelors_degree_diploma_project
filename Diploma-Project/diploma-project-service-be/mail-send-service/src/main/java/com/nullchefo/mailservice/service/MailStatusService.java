package com.nullchefo.mailservice.service;

import org.springframework.http.ResponseEntity;

import com.nullchefo.mailservice.entity.MailStatus;

public interface MailStatusService {
	 void saveStatus(MailStatus mailStatus);

	ResponseEntity<?> removeUser(Long userId);
}
