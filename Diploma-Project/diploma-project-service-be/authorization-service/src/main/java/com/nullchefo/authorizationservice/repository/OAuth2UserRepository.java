package com.nullchefo.authorizationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Repository;

import com.nullchefo.authorizationservice.domain.UserOAuth2;

@Repository
public interface OAuth2UserRepository extends JpaRepository<UserOAuth2, Long> {
	OAuth2User findByName(String name);
}
