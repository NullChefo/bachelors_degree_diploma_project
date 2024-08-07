package com.nullchefo.authorizationservice.domain;

import com.nullchefo.authorizationservice.utils.SimpleGrantedAuthoritySetConverter;
import com.nullchefo.authorizationservice.utils.StringMapConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements OAuth2User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(length = 60, unique = true, nullable = false)
    private String username;
    @Column(name = "password", length = 1000, columnDefinition = "text")
    private String password;
    private String avatarURL;
    @Convert(converter = SimpleGrantedAuthoritySetConverter.class)
    @Column(name = "granted_authorities", length = 9000, columnDefinition = "text")
    private Set<SimpleGrantedAuthority> grantedAuthorities;
    private LocalDateTime updatedAt;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean enabled = false; // email verified
    private boolean credentialsNonExpired = true;
    private boolean accountNotLocked = true;
    private boolean accountNotExpired = true;
    private boolean isOauthUser;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String university;
    private String work;

    // 5 years of storage; Migrate them to archive
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    private boolean verified;
    private LocalDateTime verifiedAt;

    // OAUTH
    private String givenName;

    private String location;

    private String thirdPartyId;

    @Convert(converter = StringMapConverter.class)
    @Column(name = "attributes", length = 9000, columnDefinition = "text", nullable = true)
    private Map<String, String> attributes;

    // Methods
    @Override
    public <A> A getAttribute(final String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<String, Object>(attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return
                grantedAuthorities;
    }

    @Override
    public String getName() {
        return username;
    }

    //	@OneToMany
    //	private Set<UserOAuth2> connectedIdentities;

}
