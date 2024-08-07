package com.stefan.userservice.controler;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stefan.userservice.DTO.PasswordChangeDTO;
import com.stefan.userservice.DTO.UserDTO;
import com.stefan.userservice.entity.User;
import com.stefan.userservice.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user/v1/")
@Slf4j
@AllArgsConstructor
public class RegistrationController {

	@Autowired
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userModel) {
		return userService.registerUser(userModel);

	}

	@GetMapping("/verifyRegistration")
	public ResponseEntity<?> verifyRegistration(@RequestParam("token") String token) {
		String result = userService.validateVerificationToken(token);
		if (result.equalsIgnoreCase("valid")) { // TODO if status 200 ok
			return ResponseEntity.ok().build(); // TODO return 200
		}
		return ResponseEntity.status(403).body("Token expired"); // TODO return status 4-- and make in FE Looks like the time for your activation has expired + button resendToken
	}


	@GetMapping("/resendVerifyToken")
	public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken) {
		if (!userService.resendVerificationToken(oldToken)){
			return ResponseEntity.status(403).body("Non valid token!");
		}
		return ResponseEntity.ok().build();


	}
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordChangeDTO passwordModel) {
		User user;
		try{
			 user = userService.findUserByEmail(passwordModel.getEmail());
		}
		catch (Exception e){
			// TODO log e
			return ResponseEntity.status(400).body("Internal problem"); // TODO handle more proper

		}
		String url = "";
		if(user!=null) {
			 String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user,token);
			url = userService.passwordResetTokenMail(user, token);
		}
		else{
			return ResponseEntity.status(404).body("User with this email does not exist!");
		}
		log.info("Reset mail url: " + url);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/savePassword")
	public ResponseEntity<?> savePassword(@RequestParam("token") String token,
							   @RequestBody PasswordChangeDTO passwordChangeDTO) {
		return userService.savePassword(token, passwordChangeDTO);
	}



	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
		User user = userService.findUserByEmail(passwordChangeDTO.getEmail());

		if(!userService.checkIfValidOldPassword(user,passwordChangeDTO.getOldPassword())) {
			return ResponseEntity.status(403).body("Invalid old Password") ;
		}
		//Save New Password
		userService.changePassword(user,passwordChangeDTO.getNewPassword());
		return ResponseEntity.ok().build();

		// TODO send email for changed password

	}




// TODO make it secure
	@DeleteMapping("/{id}")
	// @PreAuthorize("#user.id == #id")
	public ResponseEntity<?> deleteUser( // @AuthenticationPrincipal User user,
										@PathVariable Long id) {

		try{
			userService.deleteUser(id);
		}
		catch (Exception e){
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.ok().build();
	}



}
