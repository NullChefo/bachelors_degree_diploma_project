package com.nullchefo.authorizationservice.domain;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nullchefo.authorizationservice.utils.SimpleGrantedAuthoritySetConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class AuthUser {

	@Id
	private Long id;
	private String firstName;
	private String lastName;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(length = 60, unique = true, nullable = false)
	private String username;
	@Column(name = "password", length = 1000, columnDefinition = "text")
	private String password;
	@Convert(converter = SimpleGrantedAuthoritySetConverter.class)
	@Column(name = "granted_authorities", length = 1000, columnDefinition = "text")
	private Set<SimpleGrantedAuthority> grantedAuthorities;

	private LocalDateTime updatedAt;

	private LocalDateTime createdAt;
	private boolean enabled;
	private boolean credentialsNonExpired;
	private boolean accountLocked ;
	private boolean accountExpired;
}
