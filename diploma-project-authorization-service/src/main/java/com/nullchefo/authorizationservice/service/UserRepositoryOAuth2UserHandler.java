package com.nullchefo.authorizationservice.service;

import com.nullchefo.authorizationservice.domain.AuthUser;
import com.nullchefo.authorizationservice.repository.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    private final AuthUserRepository authUserRepository;

    public UserRepositoryOAuth2UserHandler(final AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public void accept(final OAuth2User user) {

        AuthUser userFromDb = this.authUserRepository.findByUsername(user.getName()).orElse(null);

        if (userFromDb == null) {
            log.trace("Saving first-time user: name=" + user.getName() + ", claims=" + user.getAttributes()
                    + ", authorities=" + user.getAuthorities());

            var attributes = user.getAttributes();

            String email = null;
            if (attributes.containsKey("email")) {
                // GitHub case: email can be present as a field but value can be null;
                var receivedEmail = attributes.get("email");
                try {
                    if (receivedEmail != null) {

                        email = receivedEmail.toString();
                    }
                } catch (Exception e) {
                    log.atError().log("Error in email");
                }

            }

            String thirdPartyId = null;
            if (attributes.containsKey("id")) {
                try {
                    thirdPartyId = attributes.get("id").toString();
                } catch (Exception e) {
                    log.atError().log("Error in thirdPartyId");
                }

            }

            String avatarUrl = null;
            if (attributes.containsKey("picture")) {
                avatarUrl = attributes.get("picture").toString();
                try {
                    avatarUrl = attributes.get("picture").toString();
                } catch (Exception e) {
                    log.atError().log("Error in picture");
                }
            }
            if (attributes.containsKey("avatar_url")) {
                avatarUrl = attributes.get("avatar_url").toString();
                try {
                    avatarUrl = attributes.get("avatar_url").toString();
                } catch (Exception e) {
                    log.atError().log("Error in avatarUrl");
                }
            }

            String location = null;
            if (attributes.containsKey("location")) {
                try {
                    location = attributes.get("location").toString();
                } catch (Exception e) {
                    log.atError().log("Error in location");
                }

            }

            if (attributes.containsKey("locale")) {
                try {
                    location = attributes.get("locale").toString();
                } catch (Exception e) {
                    log.atError().log("Error in locale");
                }

            }

            // facebook use case
            String givenName = null;
            if (attributes.containsKey("name")) {
                try {
                    givenName = attributes.get("name").toString();
                } catch (Exception e) {
                    log.atError().log("Error in name");
                }

            }

            AuthUser newUser = OAuthToUser(user);

            newUser.setEmail(email);
            newUser.setLocation(location);
            newUser.setAvatarURL(avatarUrl);
            newUser.setGivenName(givenName);
            newUser.setThirdPartyId(thirdPartyId);

            newUser.setOauthUser(true);
            newUser.setEnabled(true);
            newUser.setDeleted(false);
            newUser.setAccountNotExpired(true);
            newUser.setAccountNotLocked(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setVerified(false);

            if (email == null) {
                newUser.setEmail("");
            }

            if (givenName != null) {
                String[] names = givenName.split(" ");
                if (names.length > 1) {
                    newUser.setLastName(names[1]);
                    newUser.setFirstName(names[0]);
                }
            }

            try {
                this.authUserRepository.save(newUser);
            }
            // TODO specify exactly witch Exception
            catch (Exception e) {
                log.atError().log("User with that name already existing");
            }

        } else {

            // check for deleted

            if (userFromDb.isDeleted()) {
                log.trace("User deleted trying to enter the mainframe: name=" + user.getName() + ", claims="
                        + user.getAttributes()
                        + ", authorities=" + user.getAuthorities());

                throw new RuntimeException("User deleted");
            }

        }

    }

    @Override
    public Consumer<OAuth2User> andThen(final Consumer<? super OAuth2User> after) {
        return Consumer.super.andThen(after);
    }

    public AuthUser OAuthToUser(final OAuth2User user) {

        Map<String, String> attributes = user.getAttributes().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    if (e.getValue() == null) {
                        return "";
                    } else {
                        return e.getValue().toString();
                    }
                }));

        Set<SimpleGrantedAuthority> result = new HashSet<>();

        if (user.getAuthorities() != null) {

            result = Optional.ofNullable(user.getAuthorities())
                    .stream()
                    .flatMap(Collection::stream)
                    // TODO  .map(role -> new SimpleGrantedAuthority( role.toString()))
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                    .collect(Collectors.toSet());
        }

        return AuthUser.builder()
                .attributes(attributes)
                .username(user.getName())
                .grantedAuthorities(result)
                .build();
    }

}
