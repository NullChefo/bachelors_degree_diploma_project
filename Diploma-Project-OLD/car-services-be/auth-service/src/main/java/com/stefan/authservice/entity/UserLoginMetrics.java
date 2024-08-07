package com.stefan.authservice.entity;

import java.util.Date;

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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login_metrics")
public class UserLoginMetrics {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	private Long userId;
	private Integer loginCount = 0;
	private String username;

	private String ip;
	private String location; // TODO https://mkyong.com/java/java-find-location-using-ip-address/
	private String browser;
	private Date createdAt;
	private Date lastModified;

	// Decide if need to embed ClientInfo directly into the user

}
