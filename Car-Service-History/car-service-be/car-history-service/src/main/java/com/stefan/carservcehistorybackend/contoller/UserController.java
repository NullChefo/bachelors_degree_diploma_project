package com.stefan.carservcehistorybackend.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefan.carservcehistorybackend.repository.UserRepository;

@RestController()
@RequestMapping("/user/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public ResponseEntity<?> getCars() { // TODO add pagination

		return ResponseEntity.ok(userRepository.findAll());
	}

}
