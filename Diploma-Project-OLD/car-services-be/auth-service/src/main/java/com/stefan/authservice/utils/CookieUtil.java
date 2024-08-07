package com.stefan.authservice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
	@Value("${auth.accessTokenCookieName}")
	private String accessTokenCookieName;

	@Value("${auth.refreshTokenCookieName}")
	private String refreshTokenCookieName;
	@Value("${auth.accessTokenDurationMinutes}")
	private Long accessTokenDuration;

	@Value("${auth.refreshTokenDurationMinutes}")
	private Long refreshTokenDuration;
	@Value("${env.prod}")
	private boolean isItProduction;
	@Value("${frontEnd.url}")
	private String frontEndUrl;



	public  HttpCookie createAccessTokenCookie(String token) {

	//	String encryptedToken = SecurityCipher.encrypt(token); // TODO figue it out

		return ResponseCookie.from(accessTokenCookieName, token)
							 .maxAge(accessTokenDuration)
							 .httpOnly(isItProduction) // TODO make it true
							 .path(frontEndUrl)
							 .secure(isItProduction) // Change it
							 .build();
	}

	public HttpCookie createRefreshTokenCookie(String token) {

	//	String encryptedToken = SecurityCipher.encrypt(token); // TODO figue it out

		return ResponseCookie.from(refreshTokenCookieName, token)
							 .maxAge(refreshTokenDuration)
							 .httpOnly(isItProduction)
							 .path(frontEndUrl) // TODO check if that right
							 .secure(isItProduction)
							 .build();

	}

	public HttpCookie deleteAccessTokenCookie() {
		return ResponseCookie.from(accessTokenCookieName, "").maxAge(0).httpOnly(true).path("/").build();
	}
}
