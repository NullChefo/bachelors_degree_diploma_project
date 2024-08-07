package com.stefan.gatewayservice.dto;

import lombok.Data;

@Data
public class ClaimDTO {

	private boolean isValid;
	private String roles;
	private Long userId;

}
