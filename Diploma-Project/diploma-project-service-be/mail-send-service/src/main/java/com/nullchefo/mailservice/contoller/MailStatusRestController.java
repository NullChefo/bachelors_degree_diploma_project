package com.nullchefo.mailservice.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.mailservice.service.MailStatusService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/mail-status")
@AllArgsConstructor
public class MailStatusRestController {


	@Autowired
	private final MailStatusService mailStatusService;


	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUserFromMailList(@PathVariable final Long userId){
		return mailStatusService.removeUser(userId);
	}

}
