package com.stefan.userservice.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@NotNull
	@Email
	@Size(min = 3, message = "Not valid email. Use this format: example@examble.com")
	private String email;
	@NotNull(message = "Field empty")
	@Size(min = 1, max = 50)
	private String username;
	@NotNull(message = "Field empty")
	private String password;
	@NotNull(message = "Field empty")
	private String matchingPassword;
	@Size(max = 50)
	@NotNull(message = "Field empty")
	private String firstName;
	@Size( max = 50)
	@NotNull(message = "Field empty")
	private String lastName;

	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;

}
