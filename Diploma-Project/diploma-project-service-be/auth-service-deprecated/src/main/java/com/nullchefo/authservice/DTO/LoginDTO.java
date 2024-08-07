package com.nullchefo.authservice.DTO;

import jakarta.validation.constraints.NotBlank;
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
