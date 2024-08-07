package com.stefan.authservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	private String username;
	private String email;
	private String password;
	private String firstName;
	private boolean enabled = false; // email verified

	// TODO make it proper
//	@ElementCollection(fetch = FetchType.EAGER)
//	private List<Roles> roles;

	private String roles;

}
