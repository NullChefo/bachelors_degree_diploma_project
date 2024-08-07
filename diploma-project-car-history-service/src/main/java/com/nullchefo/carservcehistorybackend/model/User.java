package com.nullchefo.carservcehistorybackend.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	// TODO make unique = true
	@Column(unique = false, nullable = false)
	private String email;
	@Column(length = 60, unique = true, nullable = false)
	private String username;
	@Column(length = 60)
	private String password;
	private String roles;
	private boolean enabled = false; // email verified
	private boolean credentialsNonExpired = false;
	private boolean accountLocked = false;
	private boolean accountExpired = false;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.REMOVE) // TODO @JoinColumn
	private Set<Car> carSet;

}



