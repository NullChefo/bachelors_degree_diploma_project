package com.nullchefo.authorizationservice.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.nullchefo.authorizationservice.utils.SimpleGrantedAuthoritySetConverter;
import com.nullchefo.authorizationservice.utils.StringMapConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth2_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOAuth2 implements OAuth2User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	private String name;


	@Convert(converter = StringMapConverter.class)
	@Column(name = "attributes", length = 1000, columnDefinition = "text")
	private Map<String, String> attributes;


	@Convert(converter = SimpleGrantedAuthoritySetConverter.class)
	@Column(name = "granted_authorities", length = 1000, columnDefinition = "text")
	private Set<SimpleGrantedAuthority> grantedAuthorities;

	@Override
	public <A> A getAttribute(final String name) {
		return OAuth2User.super.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<String,Object>(attributes);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return
				grantedAuthorities;
	}

	@Override
	public String getName() {
		return name;
	}

}
