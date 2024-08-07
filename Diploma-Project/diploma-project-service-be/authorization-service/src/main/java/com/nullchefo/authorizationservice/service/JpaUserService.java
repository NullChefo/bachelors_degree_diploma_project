package com.nullchefo.authorizationservice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import com.nullchefo.authorizationservice.domain.AuthUser;
import com.nullchefo.authorizationservice.repository.AuthUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserService implements UserDetailsService {

	private final AuthUserRepository authUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUser user = authUserRepository.findByUsername(username)
										  .orElseThrow(() -> new UsernameNotFoundException("No such user"));
		return new User(user.getUsername(), user.getPassword(), user.getGrantedAuthorities());
	}


	public OidcUserInfo loadUser(String username) {
		AuthUser user = authUserRepository.findByUsername(username)
										  .orElseThrow(() -> new UsernameNotFoundException("No such user"));
		return OidcUserInfo.builder()
										.subject(user.getId().toString())
										.name(user.getFirstName() + " " + user.getLastName())
										.givenName(user.getFirstName())
										.familyName(user.getLastName())
										.nickname(username)
										.preferredUsername(username)
										.profile("https://example.com/" + username)
										.website("https://example.com")
										.email(user.getEmail())
										.emailVerified(user.isEnabled())
										.claim("roles", user.getGrantedAuthorities())
			//							.zoneinfo("Europe/Berlin")
			//							.locale("de-DE")
										.updatedAt(user.getUpdatedAt().toString())
										.build();
	}
}

