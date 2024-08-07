package com.stefan.authservice.contoller;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefan.authservice.DTO.ClaimDTO;
import com.stefan.authservice.DTO.LoginDTO;
import com.stefan.authservice.DTO.TokenDTO;
import com.stefan.authservice.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth/v1") // auth/v1/user?
@AllArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, final HttpServletRequest request){
		return authService.login(loginDTO, request);
	}

	@PostMapping("logout")
	public ResponseEntity<?> logout(@RequestBody TokenDTO tokenDTO) throws CredentialException {
		return authService.logout(tokenDTO);
	}

	@PostMapping("logout-all")
	public ResponseEntity<?> logoutAll(@RequestBody TokenDTO tokenDTO) throws CredentialException {
		return authService.logoutAll(tokenDTO);
	}

	@PostMapping("access-token")
	public ResponseEntity<?> accessToken(@RequestBody TokenDTO tokenDTO) throws CredentialException {
		return authService.accessToken(tokenDTO);
	}

	@PostMapping("refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody TokenDTO tokenDTO) throws CredentialException {
		return authService.refreshToken(tokenDTO);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteAllRefreshTokensFromUserId(@PathVariable final Long userId){
		return authService.deleteAllRefreshTokensFromUserId(userId);
	}

	@PostMapping("/validate/{token}")
	public ResponseEntity<ClaimDTO> verifyToken(@PathVariable final String token){
		log.info("Trying to validate token {}", token);
		return ResponseEntity.ok(authService.verifyToken(token));
	}



}
