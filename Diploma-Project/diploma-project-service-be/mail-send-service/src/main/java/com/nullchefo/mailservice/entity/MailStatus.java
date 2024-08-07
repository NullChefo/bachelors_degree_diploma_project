package com.nullchefo.mailservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mail_status")
public class MailStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long userId;
	private String type;
	private boolean sentCorrectly;

	public MailStatus(final Long userId, final String type, final boolean sentCorrectly) {
		this.userId = userId;
		this.type = type;
		this.sentCorrectly = sentCorrectly;
	}
}
