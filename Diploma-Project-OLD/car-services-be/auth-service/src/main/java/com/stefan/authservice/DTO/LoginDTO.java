package com.stefan.authservice.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	private String username;
	private String email;
	@NotBlank
	private String password;
}
