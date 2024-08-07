package com.nullchefo.mailservice.entity;

import com.nullchefo.mailservice.DTO.UserDTO;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "mail_list")
public class MailList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "user_id", nullable = false, unique = true)
	private Long userId;// TODO join

	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;

	// Add to metrics
	private Integer sentMailsForUser = 0;

	public MailList(UserDTO userDTO) {
		this.userId = userDTO.getUserId();
		this.signedForNotifications = userDTO.isSignedForNotifications();
		this.signedForAnnouncements = userDTO.isSignedForAnnouncements();
		this.signedForPromotions = userDTO.isSignedForPromotions();
	}

}
