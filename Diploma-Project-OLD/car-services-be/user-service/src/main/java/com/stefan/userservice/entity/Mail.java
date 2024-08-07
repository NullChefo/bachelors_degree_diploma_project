package com.stefan.userservice.entity;

import java.util.Map;

import lombok.Data;

@Data
public class Mail{

	private String recipient;
	private Long userId;
	private String subject;
	private String body;
	private String mailType;
	private Map<String, String> mailFields;

}
