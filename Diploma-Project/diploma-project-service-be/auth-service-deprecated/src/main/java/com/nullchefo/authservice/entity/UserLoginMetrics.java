package com.nullchefo.authservice.entity;

import java.util.Date;

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
@Getter
@Setter
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
