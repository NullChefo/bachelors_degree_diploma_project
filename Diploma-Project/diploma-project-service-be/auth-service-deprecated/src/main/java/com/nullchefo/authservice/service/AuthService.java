package com.nullchefo.authservice.service;

import java.util.Optional;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nullchefo.authservice.DTO.ClaimDTO;
import com.nullchefo.authservice.DTO.LoginDTO;
import com.nullchefo.authservice.DTO.TokenDTO;
import com.nullchefo.authservice.entity.RefreshToken;
import com.nullchefo.authservice.entity.User;
import com.nullchefo.authservice.helper.JwtHelper;
import com.nullchefo.authservice.repository.RefreshTokenRepository;
import com.nullchefo.authservice.repository.UserRepository;
import com.nullchefo.authservice.utils.ClientInfo;
import com.nullchefo.authservice.utils.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private UserAuthStatsService userAuthStatsService;
	@Autowired
	private CookieUtil cookieUtil;


	public ResponseEntity<?> login(LoginDTO loginDTO, HttpServletRequest request)
			 {

		final User user;

		if (loginDTO.getEmail() != null && !loginDTO.getEmail().equals("")) {

			 user = userRepository.findByEmail(loginDTO
														 .getEmail()
														 .toLowerCase()); // use the lowercase for email

		}
		else if (loginDTO.getUsername() != null && !loginDTO.getUsername().equals("") ){
			 user = userRepository.findByUsername(loginDTO
															  .getUsername()
															  .toLowerCase()); // use the lowercase for username
		}
		else {

			user = null;
		}

		if (user == null) {
			// throw new CredentialException("User not exist!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); //404
		}
		if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
			// throw new CredentialException("Wrong password or username!");
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Wrong password or username!"); // 406
		}

		// TODO create user not yet activated

		ClientInfo clientInfo = new ClientInfo(request);



		// TODO async

			mailSendService.sendLoginMail(user, clientInfo);


				 // TODO async
			userAuthStatsService.saveStats(user, clientInfo);



		return getResponseEntity(user);
	}

	public ResponseEntity<?> logout(TokenDTO tokenDTO){

		String refreshTokenString = tokenDTO.getRefreshToken();
		if (jwtHelper.validateRefreshToken(refreshTokenString)
				&& refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
			// valid and exists in db
			refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.status(403).body("Invalid token"); // TODO respond not ok

	}

	public ResponseEntity<?> logoutAll(TokenDTO tokenDTO) throws CredentialException {
		String refreshTokenString = tokenDTO.getRefreshToken();
		if (jwtHelper.validateRefreshToken(refreshTokenString)
				&& refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
			// valid and exists in db

			refreshTokenRepository.deleteAllByOwner_Id(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
			return ResponseEntity.ok().build();
		}

			return ResponseEntity.status(403).body("Invalid token"); // TODO respond not ok

	}

	public ResponseEntity<?> accessToken(TokenDTO tokenDTO) throws CredentialException {

		String refreshTokenString = tokenDTO.getRefreshToken();
		if (jwtHelper.validateRefreshToken(refreshTokenString)
				&& refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
			// valid and exists in db

			Optional<User> user = userRepository
					.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));

			if (user.isEmpty()){
				return ResponseEntity.status(404).body("User not found");
			}
			String accessToken = jwtHelper.generateAccessToken(user.get()); // TODO can store it in redis and remove the need for refresh token; In redis: token : valid/invalid;

			return ResponseEntity.ok(new TokenDTO(
				//	user.getId(),
					 accessToken, refreshTokenString));
		}
		return ResponseEntity.status(403).body("Invalid token"); // TODO respond not ok

	}

	public ResponseEntity<?> refreshToken(TokenDTO tokenDTO) throws CredentialException {

		String refreshTokenString = tokenDTO.getRefreshToken();
		if (jwtHelper.validateRefreshToken(refreshTokenString)
				&& refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
			// valid and exists in db

			refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));

			Optional<User> user = userRepository
					.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
			if (user.isEmpty()){
				return ResponseEntity.status(403).build();
			}
			return getResponseEntity(user.get());
		}
		 return ResponseEntity.status(403).build(); // TODO respond not ok
	}

	private ResponseEntity<?> getResponseEntity(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setOwner(user);
		refreshTokenRepository.save(refreshToken);

		String accessToken = jwtHelper.generateAccessToken(user);
		String newRefreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

		// TODO is it right that way



		HttpHeaders responseHeaders = new HttpHeaders();

		/*
		String HEADER_STRING = "Authorization";
		String TOKEN_PREFIX = "Bearer: ";
		responseHeaders.add("Access-Control-Expose-Headers", "Authorization");
		responseHeaders.add("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
		responseHeaders.add(HEADER_STRING, TOKEN_PREFIX + accessToken); // HEADER_STRING == Authorization
		 */

		responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(accessToken).toString());

		responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(newRefreshTokenString).toString()); // TODO check why it doesn't send this

//TODO when fixed above remove this
		return ResponseEntity.ok().headers(responseHeaders).body(new TokenDTO(
				//	user.getId(),
				accessToken, newRefreshTokenString));

	}
	public ResponseEntity<?> deleteAllRefreshTokensFromUserId(final Long userId) {
		try{
			refreshTokenRepository.existsById(userId);
		}
		catch (Exception e){
			return ResponseEntity.status(409).body("There is a problem with refresh token deletion. Please try again later!");
		}

		return ResponseEntity.ok().build();

	}

	public ClaimDTO verifyToken(final String token) {

			return jwtHelper.verifyAccessToken(token);

	}
}
