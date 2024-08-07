package com.stefan.userservice.DTO;

import javax.validation.constraints.NotNull;

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
