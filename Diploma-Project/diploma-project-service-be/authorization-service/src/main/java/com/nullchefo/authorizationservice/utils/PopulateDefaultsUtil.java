package com.nullchefo.authorizationservice.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import com.nullchefo.authorizationservice.service.JpaRegisteredClientRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PopulateDefaultsUtil {

	@Value("${oauth.populateRegisterClient}")
	private boolean enabled;

	private final JpaRegisteredClientRepository jpaRegisteredClientRepository;

	public void PopulateDefaults() {

		if (!enabled) {
			return;
		}

		RegisteredClient registeredClient = RegisteredClient.withId(
																	//	UUID.randomUUID().toString()
																	"angular-client"
																   )
															// gateway		this.gatewayClientId
															.clientId("angular-client")
											.clientSecret("{noop}secret")
															// secret		this.gatewayClientSecret
													//		.clientSecret("{noop}secret")
												.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
												.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
												.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
															.redirectUri("http://127.0.0.1:4200")
															.redirectUri("http://127.0.0.1:4200/auth/authorized")
															.redirectUri("http://127.0.0.1:4200/silent-renew.html")
															.redirectUri("http://127.0.0.1:3000/authorized")

															.tokenSettings(TokenSettings
																				   .builder().accessTokenTimeToLive(
																			Duration.ofDays(1)).build())
															.scopes(scopes -> scopes.addAll(List.of(
																	OidcScopes.OPENID,
																	OidcScopes.PROFILE,
																	OidcScopes.EMAIL,
																	"user.read",
																	"user.write",
																	"offline_access"
																	)))
															.clientSettings(ClientSettings
																					.builder()
																					.requireProofKey(true) // PKCE
																					.requireAuthorizationConsent(false)
																					// This way 1 token is limited to 1 or more resources insted of multiple ones
																					//																   .setting("settings.client.allowed.resources", allowedResources)
																					.build())
															.build();

		jpaRegisteredClientRepository.save(registeredClient);

		/*
		registeredClient = RegisteredClient.withId(
												   //	UUID.randomUUID().toString()
												   "00000000-0000-0000-0000-00050000000"
												  )
										   .clientId("angularCodeRefreshTokens")
										   .clientSecret("{noop}angularCodeTokens")

										   .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
										   .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
										   .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
										   .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
										   .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)

										   .redirectUri("http://localhost:9000/login/oauth2/code/gateway-client-oidc")
										   // http://127.0.0.1:9000/authorized   this.gatewayAuthorizeUri
										   .redirectUri("http://127.0.0.1:9000/authorized")
										   .redirectUri("http://127.0.0.1:8089")
										   .redirectUri("http://127.0.0.1:4200/authorized")
										   .redirectUri("http://127.0.0.1:4200/error")
										   .redirectUri("http://127.0.0.1:4200/client")
										   .redirectUri("http://127.0.0.1:4200/")
										   .redirectUri("http://127.0.0.1:4200/")


										   .tokenSettings(TokenSettings
																  .builder().accessTokenTimeToLive(
														   Duration.ofDays(1)).build())

										   //.postLogoutRedirectUri("http://127.0.0.1:8080/index")
										   .scopes(scopes -> scopes.addAll(List.of(
												   OidcScopes.OPENID,
												   OidcScopes.PROFILE,
												   OidcScopes.EMAIL,
												   "offline_access"
																				  )))
										   .clientSettings(ClientSettings
																   .builder().requireProofKey(true) // PKCE
																   .requireAuthorizationConsent(false)
																   .build())
										   .build();

		jpaRegisteredClientRepository.save(registeredClient);

		// client
		registeredClient = RegisteredClient.withId("00000000-0000-0000-0000-000000000002")
										   .clientId("demo-client")
										   .clientSecret("{noop}secret")
										   .clientAuthenticationMethods(methods -> methods.addAll(
												   List.of(
														   ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
														   ClientAuthenticationMethod.CLIENT_SECRET_POST,
														   ClientAuthenticationMethod.NONE
														  )))
										   .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
										   .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
										   .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
										   .redirectUri("http://127.0.0.1:9095/client/callback")
										   .redirectUri("http://127.0.0.1:9095/client/authorized")
										   .redirectUri("http://127.0.0.1:9095/client")
										   .redirectUri("http://127.0.0.1:9095/login/oauth2/code/spring-authz-server")
										   .redirectUri("https://oauth.pstmn.io/v1/callback")
										   .scopes(scopes -> scopes.addAll(List.of(
												   OidcScopes.OPENID,
												   OidcScopes.PROFILE,
												   OidcScopes.EMAIL,
												   "offline_access"
																				  )))
										   .clientSettings(ClientSettings
																   .builder()
																   .requireAuthorizationConsent(false)
																   .build())
										   .build();

		jpaRegisteredClientRepository.save(registeredClient);

		// client pkce
		registeredClient = RegisteredClient.withId("00000000-0000-0000-0000-000000000003")
										   .clientId("demo-client-pkce")
										   .clientAuthenticationMethods(methods -> methods.addAll(
												   List.of(
														   ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
														   ClientAuthenticationMethod.CLIENT_SECRET_POST,
														   ClientAuthenticationMethod.NONE
														  )))
										   .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
										   .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
										   .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
										   .redirectUri("http://127.0.0.1:9095/client/callback")
										   .redirectUri("http://127.0.0.1:9095/client/authorized")
										   .redirectUri("http://127.0.0.1:9095/client")
										   .redirectUri("http://127.0.0.1:9095/login/oauth2/code/spring-authz-server")
										   .redirectUri("https://oauth.pstmn.io/v1/callback")
										   .scopes(scopes -> scopes.addAll(List.of(
												   OidcScopes.OPENID,
												   OidcScopes.PROFILE,
												   OidcScopes.EMAIL,
												   "offline_access"
																				  )))
										   .clientSettings(ClientSettings
																   .builder()
																   .requireProofKey(true)
																   .requireAuthorizationConsent(false)
																   .build())
										   .build();

		jpaRegisteredClientRepository.save(registeredClient);

		// client opaque
		registeredClient = RegisteredClient.withId("00000000-0000-0000-0000-000000000004")
										   .clientId("demo-client-opaque")
										   .clientSecret("{noop}secret")
										   .clientAuthenticationMethods(methods -> methods.addAll(
												   List.of(
														   ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
														   ClientAuthenticationMethod.CLIENT_SECRET_POST,
														   ClientAuthenticationMethod.NONE
														  )))
										   .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
										   .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
										   .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
										   .tokenSettings(TokenSettings
																  .builder()
																  .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
																  .build())
										   .redirectUri("http://127.0.0.1:9095/client/callback")
										   .redirectUri("http://127.0.0.1:9095/client/authorized")
										   .redirectUri("http://127.0.0.1:9095/client")
										   .redirectUri("http://127.0.0.1:9095/login/oauth2/code/spring-authz-server")
										   .redirectUri("https://oauth.pstmn.io/v1/callback")
										   .scopes(scopes -> scopes.addAll(List.of(
												   OidcScopes.OPENID,
												   OidcScopes.PROFILE,
												   OidcScopes.EMAIL,
												   "offline_access"
																				  )))
										   .clientSettings(ClientSettings
																   .builder()
																   .requireAuthorizationConsent(false)
																   .build())
										   .build();

		jpaRegisteredClientRepository.save(registeredClient);

		// client pkce opaque
		registeredClient = RegisteredClient.withId("00000000-0000-0000-0000-000000000005")
										   .clientId("demo-client-pkce-opaque")
										   .clientSecret("{noop}secret")
										   .clientAuthenticationMethods(methods -> methods.addAll(
												   List.of(
														   ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
														   ClientAuthenticationMethod.CLIENT_SECRET_POST,
														   ClientAuthenticationMethod.NONE
														  )))
										   .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
										   .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
										   .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
										   .tokenSettings(TokenSettings.builder().accessTokenFormat(
												   OAuth2TokenFormat.REFERENCE).build())
										   .redirectUri("http://127.0.0.1:9095/client/callback")
										   .redirectUri("http://127.0.0.1:9095/client/authorized")
										   .redirectUri("http://127.0.0.1:9095/client")
										   .redirectUri("http://127.0.0.1:9095/login/oauth2/code/spring-authz-server")
										   .redirectUri("https://oauth.pstmn.io/v1/callback")
										   .scopes(scopes -> scopes.addAll(List.of(
												   OidcScopes.OPENID,
												   OidcScopes.PROFILE,
												   OidcScopes.EMAIL,
												   "offline_access"
																				  )))
										   .clientSettings(ClientSettings
																   .builder()
																   .requireProofKey(true)
																   .requireAuthorizationConsent(false)
																   .build())
										   .build();
		jpaRegisteredClientRepository.save(registeredClient);

		*/

	}

}
