package com.stefan.mailservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.stefan.mailservice.DTO.UserDTO;

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
	private Integer sendedMailsForUser = 0;


	public MailList(UserDTO userDTO){
		this.userId = userDTO.getUserId();
		this.signedForNotifications = userDTO.isSignedForNotifications();
		this.signedForAnnouncements = userDTO.isSignedForAnnouncements();
		this.signedForPromotions = userDTO.isSignedForPromotions();
	}


}
