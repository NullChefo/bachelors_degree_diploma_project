package com.nullchefo.authorizationservice.service;

import com.nullchefo.authorizationservice.domain.AuthUser;
import com.nullchefo.authorizationservice.repository.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JpaUserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    public JpaUserService(final AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUser user = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        // TODO check if account is locked, expired
        if (!user.isEnabled()) {
            log.info("User is not enabled. Please your account by clicking the link sent to your email!");
            throw new UsernameNotFoundException(
                    "User is not enabled. Please your account by clicking the link sent to your email!");
        }

        if (user.isOauthUser()) {
            log.info("Cannot login with OAuth! Please use the login form");
            throw new IllegalAccessError("Cannot login with OAuth! Please use the login form");
        }

        return new User(user.getUsername(), user.getPassword(), user.getGrantedAuthorities());
    }
}

