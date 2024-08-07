package com.stefan.mailservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	@Column(name = "id", nullable = false)
	private Long id;
	private Long userId;
	private String type;
	private boolean sendedCorrectrly;

	public MailStatus(final Long userId, final String type, final boolean sendedCorrectrly) {
		this.userId = userId;
		this.type = type;
		this.sendedCorrectrly = sendedCorrectrly;
	}
}
