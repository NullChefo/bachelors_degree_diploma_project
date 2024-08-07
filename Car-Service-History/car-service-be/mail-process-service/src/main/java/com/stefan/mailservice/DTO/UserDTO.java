package com.stefan.mailservice.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDTO {

	@NotNull
	private Long userId;
	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;

}
