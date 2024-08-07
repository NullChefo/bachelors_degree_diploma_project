package com.nullchefo.authorizationservice.repository;

import com.nullchefo.authorizationservice.domain.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {

    //	@Cacheable(value = "usersCache")
    Optional<AuthUser> findByUsername(String username);

    void deleteById(Long id);

    AuthUser findByEmail(String email);

    AuthUser findByUsernameIgnoreCase(String username);
}

