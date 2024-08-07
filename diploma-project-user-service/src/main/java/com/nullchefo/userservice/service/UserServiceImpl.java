package com.nullchefo.userservice.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.nullchefo.userservice.DTO.PasswordChangeDTO;
import com.nullchefo.userservice.DTO.UserDTO;
import com.nullchefo.userservice.DTO.UserMailListDTO;
import com.nullchefo.userservice.entity.EmailVerificationToken;
import com.nullchefo.userservice.entity.PasswordResetToken;
import com.nullchefo.userservice.entity.User;
import com.nullchefo.userservice.repository.EmailVerificationTokenRepository;
import com.nullchefo.userservice.repository.PasswordResetTokenRepository;
import com.nullchefo.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	// TODO READ ABOUT completableFuture https://www.baeldung.com/java-completablefuture
	// TODO Catch Exceptions on the Thread.ofVirtual() or the completableFuture

	private final UserRepository userRepository;
	private final EmailVerificationTokenRepository verificationTokenRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final MailProducerService mailProducerService;
	private final WebClient.Builder webClientBuilder;
	private final PasswordEncoder passwordEncoder;
	private final String authorizationService;
	private final String mailProcess;
	private final String mailSend;

	public UserServiceImpl(
			final UserRepository userRepository,
			final EmailVerificationTokenRepository verificationTokenRepository,
			final PasswordResetTokenRepository passwordResetTokenRepository,
			final MailProducerService mailProducerService,
			final WebClient.Builder webClientBuilder,
			final PasswordEncoder passwordEncoder,
			@Value("${services-list.authorization-service}") final String authorizationService,
			@Value("${services-list.mail-process-service}") final String mailProcess,
			@Value("${services-list.mail-send-service}") final String mailSend) {
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.mailProducerService = mailProducerService;
		this.webClientBuilder = webClientBuilder;
		this.passwordEncoder = passwordEncoder;
		this.authorizationService = authorizationService;
		this.mailProcess = mailProcess;
		this.mailSend = mailSend;
	}

	@Override
	public ResponseEntity<?> registerUser(UserDTO userDTO) {
		// TODO do regex on the password - frond end & backend
		// TODO check if password matches
		User user = buildUser(userDTO);

		// The old Responce entity
		try {
			userRepository.save(user);
		} catch (Exception e) {
			log.error("DB error for user with email: " + user.getEmail() + " with exeption: \n" + e);
			System.out.println(e.toString());
			return ResponseEntity.status(409).body("User already exists"); // TODO specify witch field

		}
		final String token = UUID.randomUUID().toString();

		// TODO use async/ completable feature / use virtual threads

		saveVerificationTokenForUser(token, user);

		// TODO use async/ completable feature / use virtual threads

		mailProducerService.sendEmailVerification(user, token); // maybe async

		sighUserToMailList(new UserMailListDTO(user.getId(), userDTO));

		log.trace(String.valueOf(user));
		return ResponseEntity.ok().build();
	}

	@Override
	public void saveVerificationTokenForUser(String token, User user) {
		EmailVerificationToken verificationToken
				= new EmailVerificationToken(user, token);

		verificationTokenRepository.save(verificationToken);
	}

	@Override
	public String validateVerificationToken(String token) {
		EmailVerificationToken verificationToken
				= verificationTokenRepository.findByToken(token);

		if (verificationToken == null) {
			return "invalid";
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpirationTime().getTime()
				- cal.getTime().getTime()) <= 0) {
			verificationTokenRepository.delete(verificationToken);
			return "expired";
		}

		user.setEnabled(true);
		userRepository.save(user);
		verificationTokenRepository.delete(verificationToken);
		log.trace(String.valueOf(verificationToken));
		return "valid";
	}

	@Override
	public EmailVerificationToken generateNewVerificationToken(String oldToken) {
		EmailVerificationToken verificationToken
				= verificationTokenRepository.findByToken(oldToken);
		if (verificationToken == null) {
			return null;
		}
		verificationTokenRepository.delete(verificationToken);

		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepository.save(verificationToken);
		log.trace(String.valueOf(verificationToken));
		return verificationToken;
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken passwordResetToken
				= new PasswordResetToken(user, token);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public String validatePasswordResetToken(String token) {
		PasswordResetToken passwordResetToken
				= passwordResetTokenRepository.findByToken(token);

		if (passwordResetToken == null) {
			return "invalid";
		}

		User user = passwordResetToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((passwordResetToken.getExpirationTime().getTime()
				- cal.getTime().getTime()) <= 0) {
			passwordResetTokenRepository.delete(passwordResetToken);
			return "expired";
		}

		return "valid";
	}

	@Override
	public Optional<User> getUserByPasswordResetToken(String token) {
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
	}

	@Override
	public void changePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	public boolean checkIfValidOldPassword(User user, String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public void deleteUser(final Long id) {

		try {
			userRepository.deleteById(id);
		} catch (Exception e) {

			return;

		}

		// TODO use async/ completable feature / use virtual threads
		webClientBuilder.build().delete()
						.uri(mailSend + "/mail-status/" + id)
						.retrieve().toBodilessEntity().block();

		// TODO use async/ completable feature / use virtual threads

		// delete from mail_list
		webClientBuilder.build().delete()
						.uri(mailProcess + "/mail-list/" + id)
						.retrieve().toBodilessEntity().block();

		// TODO use async/ completable feature / use virtual threads

		// delete  user_login_metrics
		webClientBuilder.build().delete()
						.uri(authorizationService + "/auth-metrics" + id)
						.retrieve();

		// TODO use async/ completable feature / use virtual threads

		// delete refresh_token
		webClientBuilder.build().delete()
						.uri(authorizationService + "/authorization" + id)
						.retrieve().toBodilessEntity().block();

	}

	@Override
	public ResponseEntity<?> savePassword(final String token, final PasswordChangeDTO passwordChangeDTO) {

		if (passwordChangeDTO.getNewPassword() == null) {
			return ResponseEntity.status(403).body("New password can not be empty!");
		}
		String result = validatePasswordResetToken(token);

		if (!result.equalsIgnoreCase("valid")) {
			return ResponseEntity.status(403).body("Invalid token!");
		}
		Optional<User> user = getUserByPasswordResetToken(token);
		if (user.isPresent()) {
			changePassword(user.get(), passwordChangeDTO.getNewPassword());
			return ResponseEntity.ok().build();

			// TODO Remove from token store

		} else {
			return ResponseEntity.status(403).body("Invalid token!");
		}
	}

	@Override
	public boolean resendVerificationToken(final String oldToken) {
		EmailVerificationToken emailVerificationToken
				= generateNewVerificationToken(oldToken);
		if (emailVerificationToken == null) {
			return false;
		}
		User user = emailVerificationToken.getUser();
		mailProducerService.sendEmailVerification(user, emailVerificationToken.getToken());
		return true;
	}

	@Override
	public String passwordResetTokenMail(final User user, String token) {
		return mailProducerService.passwordResetTokenMail(user, token);
	}

	private void sighUserToMailList(final UserMailListDTO userMailListDTO) {
		// TODO use async/ completable feature / use virtual threads

		ResponseEntity<?> s = webClientBuilder.build().post()
											  .uri(mailProcess + "/mail/")
											  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
											  .body(BodyInserters.fromValue(userMailListDTO))
											  .retrieve().bodyToMono(ResponseEntity.class).block();

	}

	private User buildUser(final UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getEmail().toLowerCase());
		user.setUsername(userDTO.getUsername().toLowerCase());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setBirthday(userDTO.getBirthday());
		// TODO setGrantedAuthorities
		user.setGrantedAuthorities("ROLE_USER");
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		return user;
	}

}
