package com.nullchefo.userservice.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
	@Size(max = 50)
	@NotNull(message = "Field empty")
	private String lastName;
	private String grantedAuthorities;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message = "Are you a time traveler?")
	private LocalDate birthday;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;

	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;

}
