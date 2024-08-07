package com.stefan.userservice.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.stefan.userservice.DTO.PasswordChangeDTO;
import com.stefan.userservice.DTO.UserDTO;
import com.stefan.userservice.entity.EmailVerificationToken;
import com.stefan.userservice.entity.User;

public interface UserService {
	ResponseEntity<?> registerUser(UserDTO userModel);

	void saveVerificationTokenForUser(String token, User user);

	String validateVerificationToken(String token);

	EmailVerificationToken generateNewVerificationToken(String oldToken);

	User findUserByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	String validatePasswordResetToken(String token);

	Optional<User> getUserByPasswordResetToken(String token);

	void changePassword(User user, String newPassword);

	boolean checkIfValidOldPassword(User user, String oldPassword);

	void deleteUser(Long id);

	ResponseEntity<?> savePassword(String token, PasswordChangeDTO passwordChangeDTO);

	boolean resendVerificationToken(String oldToken);

	String passwordResetTokenMail(User user, String token);
}
