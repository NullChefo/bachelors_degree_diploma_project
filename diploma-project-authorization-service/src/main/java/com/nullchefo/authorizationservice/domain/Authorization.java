package com.nullchefo.authorizationservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "authorizations", indexes = {
        @Index(name = "idx_authorization_attributes", columnList = "attributes")
})
@Getter
@Setter
public class Authorization {
    @Id
    //	@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Column(length = 9000)
    private String authorizedScopes;
    // TODO fix
    @Column(length = 99000)
    private String attributes;
    @Column(length = 500)
    private String state;

    @Column(length = 9000)
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;

    @Column(length = 9000)
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Column(length = 2000)
    private String accessTokenMetadata;
    private String accessTokenType;
    @Column(length = 1000)
    private String accessTokenScopes;
    @Column(length = 9000)
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Column(length = 2000)
    private String refreshTokenMetadata;
    @Column(length = 9000)
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Column(length = 2000)
    private String oidcIdTokenMetadata;
    @Column(length = 9000)
    private String oidcIdTokenClaims;

}


