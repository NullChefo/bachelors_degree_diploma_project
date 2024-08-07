package com.nullchefo.authservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {
	// private Long userId;
	private String accessToken;
	private String refreshToken;
}
