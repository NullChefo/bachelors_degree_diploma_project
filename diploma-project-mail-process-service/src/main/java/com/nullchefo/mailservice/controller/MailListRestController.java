package com.nullchefo.mailservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.mailservice.DTO.UserDTO;
import com.nullchefo.mailservice.entity.MailList;
import com.nullchefo.mailservice.service.MailListService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/mail-list")
@SecurityRequirement(name = "Bearer Authentication")
public class MailListRestController {

	/*

	GET retrieves resources.
	POST submits new data to the server.
	PUT updates existing data.
	DELETE removes data.
	 */

	private final MailListService mailListService;

	public MailListRestController(final MailListService mailListService) {
		this.mailListService = mailListService;
	}

	@GetMapping("/")
	public ResponseEntity<List<MailList>> getMailListInfoForUsers() {
		return ResponseEntity.ok(mailListService.getAllUsers());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<MailList> getMailListInfoForUser(@PathVariable final Long userId) {
		return mailListService.getMailListInfoForUser(userId);
	}

	@PostMapping
	public ResponseEntity<?> registerUserIntoMailList(@RequestBody final UserDTO userDTO) throws Exception {

		return mailListService.registerUser(userDTO);

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> removeUserFromMailList(@PathVariable final Long userId) {
		return mailListService.removeUser(userId);
	}

	@GetMapping("/announcement/{userId}")
	public ResponseEntity<?> toggleAnnouncements(@PathVariable final Long userId) {

		return mailListService.toggleAnnouncements(userId);

	}

	@GetMapping("/promotion/{userId}")
	public ResponseEntity<?> togglePromotions(@PathVariable final Long userId) {

		return mailListService.togglePromotions(userId);
	}

	@GetMapping("/notification/{userId}")
	public ResponseEntity<?> toggleNotifications(@PathVariable final Long userId) {
		return mailListService.toggleNotifications(userId);
	}

}
