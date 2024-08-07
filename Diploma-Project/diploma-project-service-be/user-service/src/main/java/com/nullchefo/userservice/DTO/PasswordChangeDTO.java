package com.nullchefo.userservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeDTO {

	@NotNull
	private String email;
	@NotNull
	private String oldPassword;
	@NotNull
	private String newPassword;

}
