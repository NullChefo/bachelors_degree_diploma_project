package com.nullchefo.mailservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

	@NotNull
	private Long userId;
	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;

}
