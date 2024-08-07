package com.nullchefo.authorizationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.authorizationservice.domain.AuthUser;

@Repository
public interface AuthUserRepository  extends JpaRepository<AuthUser, String> {

	Optional<AuthUser> findByUsername(String username);
}
