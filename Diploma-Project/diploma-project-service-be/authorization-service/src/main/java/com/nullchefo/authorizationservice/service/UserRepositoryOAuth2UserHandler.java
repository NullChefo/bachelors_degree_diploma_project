package com.nullchefo.authorizationservice.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.nullchefo.authorizationservice.domain.UserOAuth2;
import com.nullchefo.authorizationservice.repository.OAuth2UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

	public UserRepositoryOAuth2UserHandler(final OAuth2UserRepository oAuth2UserRepository) {
		this.oAuth2UserRepository = oAuth2UserRepository;
	}

	private final OAuth2UserRepository oAuth2UserRepository;


	@Override
	public void accept(final OAuth2User user) {
		if (this.oAuth2UserRepository.findByName(user.getName()) == null) {
			log.info("Saving first-time user: name=" + user.getName() + ", claims=" + user.getAttributes() + ", authorities=" + user.getAuthorities());

			try {
				this.oAuth2UserRepository.save(OAuthToUser(user));
			}
			// TODO specify exactly witch Exception
			catch (Exception e){
				log.atError().log("User with that name already existing");
			}

		}



	}

	@Override
	public Consumer<OAuth2User> andThen(final Consumer<? super OAuth2User> after) {
		return Consumer.super.andThen(after);
	}


	public UserOAuth2 OAuthToUser(final OAuth2User user) {

		Map<String,String> attributes = user.getAttributes().entrySet().stream()
									   .collect(Collectors.toMap(Map.Entry::getKey, e -> {if( e.getValue() == null ){ return "";
									   }else {
										   return e.getValue().toString();
									   }} ));


		Set<SimpleGrantedAuthority> result = new HashSet<>();


		if(user.getAuthorities() != null){


		result =  Optional.ofNullable(user.getAuthorities())
						  .stream()
						  .flatMap(Collection::stream)
						  // TODO  .map(role -> new SimpleGrantedAuthority( role.toString()))
						  .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
						  .collect(Collectors.toSet());
		}

		return UserOAuth2.builder()
						 .attributes(attributes)
						 .name(user.getName())
						 .grantedAuthorities( result )
						 .build();
	}


}
