package com.stefan.gatewayservice.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stefan.gatewayservice.dto.ClaimDTO;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtHelper {

	static final String issuer = "Diploma Project";
	private Algorithm accessTokenAlgorithm;
	private JWTVerifier accessTokenVerifier;


	public JwtHelper(
			@Value("${auth.accessTokenSecret}") String accessTokenSecret)

		 {
		accessTokenAlgorithm = Algorithm.HMAC512(accessTokenSecret);
		accessTokenVerifier = JWT.require(accessTokenAlgorithm)
								 .withIssuer(issuer)
								 .build();

	}

	/*
	public Optional<DecodedJWT>  verifyAccessToken(String accessToken) {
		try {
			return Optional.of(accessTokenVerifier.verify(accessToken));
		} catch (JWTVerificationException e) {
			log.error("invalid access token", e);
		}
		return Optional.empty();

	}

	 */


	public DecodedJWT rawVerify(String token) {

		return accessTokenVerifier.verify(token);
	}



	private Optional<DecodedJWT> decodeAccessToken(String token) {
		try {
			return Optional.of(accessTokenVerifier.verify(token));

		} catch (JWTVerificationException e) {

			log.error("invalid access token", e); // Pass the TokenExpiredException

		}
		return Optional.empty();
	}


	/*
	// with try catch

		private Optional<DecodedJWT> decodeAccessToken(String token) {
		try {
			return Optional.of(accessTokenVerifier.verify(token));

		} catch (JWTVerificationException e) {

			log.error("invalid access token", e); // Pass the TokenExpiredException

		}
		return Optional.empty();
	}


	 */


	public boolean validateAccessToken(String token) {
		return decodeAccessToken(token).isPresent();

	}


	public ClaimDTO verifyAccessToken(String accessToken) {
		ClaimDTO claimDTO = new ClaimDTO();
		try {
			DecodedJWT decodedJWT = accessTokenVerifier.verify(accessToken);
			// TODO send get claims in DTO ;
			claimDTO.setRoles(decodeAccessToken(accessToken).get().getClaim("roles").asString());
			claimDTO.setUserId(decodeAccessToken(accessToken).get().getClaim("userId").asLong());

		} catch (JWTVerificationException ex) {
			System.out.println("Verify JWT token fail: " + ex.getMessage());
			claimDTO.setValid(false);
			return claimDTO;
		}
		claimDTO.setValid(true);
		return claimDTO;
	}


}

