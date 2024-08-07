package com.nullchefo.carservcehistorybackend.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.carservcehistorybackend.repository.UserRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController()
@RequestMapping("/user/v1/")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

	private final UserRepository userRepository;

	public UserController(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/")
	public ResponseEntity<?> getCars() { // TODO add pagination

		return ResponseEntity.ok(userRepository.findAll());
	}

}
