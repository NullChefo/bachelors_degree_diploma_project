package com.nullchefo.authservice.helper;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nullchefo.authservice.DTO.ClaimDTO;
import com.nullchefo.authservice.entity.RefreshToken;
import com.nullchefo.authservice.entity.User;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtHelper {


	private long accessTokenExpirationMs;
	private long refreshTokenExpirationMs;
	private Algorithm accessTokenAlgorithm;
	private Algorithm refreshTokenAlgorithm;
	private JWTVerifier accessTokenVerifier;
	private JWTVerifier refreshTokenVerifier;

	@Value("${project.name}")
	private String projectName;

	public JwtHelper(
			@Value("${auth.accessTokenSecret}") String accessTokenSecret,
			@Value("${auth.refreshTokenSecret}") String refreshTokenSecret,
			@Value("${auth.refreshTokenDurationMinutes}") int refreshTokenExpirationDays,
			@Value("${auth.accessTokenDurationMinutes}") int accessTokenExpirationMinutes) {
		accessTokenExpirationMs =
				(long) accessTokenExpirationMinutes * 60 * 1000; // accessTokenExpirationMinutes * 60 * 1000
		refreshTokenExpirationMs =
				(long) refreshTokenExpirationDays * 60 * 1000; // refreshTokenExpirationDays * 24 * 60 * 60 * 1000
		accessTokenAlgorithm = Algorithm.HMAC512(accessTokenSecret);
		refreshTokenAlgorithm = Algorithm.HMAC512(refreshTokenSecret);
		accessTokenVerifier = JWT.require(accessTokenAlgorithm)
								 .withIssuer(projectName)
								 .build();
		refreshTokenVerifier = JWT.require(refreshTokenAlgorithm)
								  .withIssuer(projectName)
								  .build();
	}

	public String generateAccessToken(User user) {
		return JWT.create()
				  .withIssuer(projectName)
				  .withSubject(user.getId().toString())
				  .withIssuedAt(new Date())
				  .withExpiresAt(new Date(new Date().getTime() + accessTokenExpirationMs))
				  .withClaim("roles", user.getRoles())
				  .withClaim("userId", user.getId())
				  .sign(accessTokenAlgorithm);
	}

	public ClaimDTO verifyAccessToken(String accessToken) {
		ClaimDTO claimDTO = new ClaimDTO();
		try {
			DecodedJWT decodedJWT = accessTokenVerifier.verify(accessToken);
			System.out.println("Verify JWT token success.");
			System.out.println(decodedJWT.getClaims());

			// TODO send get claims in DTO ;
			claimDTO.setRoles(decodeRefreshToken(accessToken).get().getClaim("roles").asString());
			claimDTO.setUserId(decodeRefreshToken(accessToken).get().getClaim("userId").asLong());

		} catch (JWTVerificationException ex) {

			System.out.println("Verify JWT token fail: " + ex.getMessage());

			claimDTO.setValid(false);
			return claimDTO;

		}

		claimDTO.setValid(true);
		return claimDTO;
	}

	public String generateRefreshToken(User user, RefreshToken refreshToken) {
		return JWT.create()
				  .withIssuer(projectName)
				  .withSubject(user.getId().toString())
				  .withClaim("tokenId", refreshToken.getId().toString())
				  .withIssuedAt(new Date())
				  .withExpiresAt(new Date((new Date()).getTime() + refreshTokenExpirationMs))
				  .sign(refreshTokenAlgorithm);
	}

	private Optional<DecodedJWT> decodeAccessToken(String token) {
		try {
			return Optional.of(accessTokenVerifier.verify(token));
		} catch (JWTVerificationException e) {
			log.error("invalid access token", e);
		}
		return Optional.empty();
	}

	private Optional<DecodedJWT> decodeRefreshToken(String token) {
		try {
			return Optional.of(refreshTokenVerifier.verify(token));
		} catch (JWTVerificationException e) {
			log.error("invalid refresh token", e);
		}
		return Optional.empty();
	}

	public boolean validateAccessToken(String token) {
		return decodeAccessToken(token).isPresent();
	}

	public boolean validateRefreshToken(String token) {
		return decodeRefreshToken(token).isPresent();
	}

	public Long getUserIdFromAccessToken(String token) {
		String userId = decodeAccessToken(token).get().getSubject();
		return Long.parseLong(userId);
	}

	public Long getUserIdFromRefreshToken(String token) {
		String userId = decodeRefreshToken(token).get().getSubject();
		return Long.parseLong(userId);
	}

	public Long getTokenIdFromRefreshToken(String token) {
		String tokenId = decodeRefreshToken(token).get().getClaim("tokenId").asString();
		return Long.parseLong(tokenId);
	}
}

