package com.nullchefo.userservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserMailListDTO {

	@NotNull
	private Long userId;
	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;

	public UserMailListDTO(Long userId, UserDTO userDTO) {
		this.userId = userId;
		this.signedForAnnouncements = userDTO.isSignedForAnnouncements();
		this.signedForPromotions = userDTO.isSignedForPromotions();
		this.signedForNotifications = userDTO.isSignedForNotifications();
	}

}
