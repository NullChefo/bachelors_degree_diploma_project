package com.stefan.mailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefan.mailservice.DTO.UserDTO;
import com.stefan.mailservice.service.MailListService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/mail-list")
@AllArgsConstructor
public class MailListRestController {



	/*

	GET retrieves resources.
	POST submits new data to the server.
	PUT updates existing data.
	DELETE removes data.
	 */

	@Autowired
	private MailListService mailListService;


	@GetMapping("/{userId}")
	public void getMailListInfoForUser(@PathVariable final String userId){
		return ;
	}


	@PostMapping
	public ResponseEntity<?> registerUserIntoMailList(@RequestBody final UserDTO userDTO) throws Exception {

		return mailListService.registerUser(userDTO);

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUserFromMailList(@PathVariable final Long userId){
		return mailListService.removeUser(userId);
	}


	@GetMapping("/announcement/{userId}")
	public ResponseEntity<?> toggleAnnouncements(@PathVariable final Long userId){


		return mailListService.toggleAnnouncements(userId);

	}

	@GetMapping("/promotion/{userId}")
	public ResponseEntity<?> togglePromotions(@PathVariable final Long userId){

		return mailListService.togglePromotions(userId);
	}

	@GetMapping("/notification/{userId}")
	public ResponseEntity<?> toggleNotifications(@PathVariable final Long userId){
		return mailListService.toggleNotifications(userId);
	}






}
