package com.stefan.gatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.stefan.gatewayservice.dto.ClaimDTO;
import com.stefan.gatewayservice.util.JwtHelper;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFactory extends AbstractGatewayFilterFactory<AuthenticationFactory.Config> {

	public AuthenticationFactory() {
		super(Config.class);
	}

	@Autowired
	private JwtHelper jwtHelper;

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			String authHeaders = this.getAuthHeader(request);

			//	System.out.printf(authHeaders);

			if (!authHeaders.equals("")) {

				System.out.println("auth header " + this.getAuthHeader(request));

				System.out.println("auth header is missing " + this.isAuthMissing(request));

				if (this.isAuthMissing(request)) {
					return this.onError(
							exchange,
							"Authorization header is missing in request",
							HttpStatus.UNAUTHORIZED);
				}
				String authHeader = this.getAuthHeader(request);

				String token = authHeader.replace("Bearer ", "");

				try {
					jwtHelper.rawVerify(token);
				} catch (TokenExpiredException e) {

					return this.onError(exchange, e.toString(), HttpStatus.UNAUTHORIZED);
				}

				/*

				if (!jwtHelper.validateAccessToken(token)){

					return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
				}
				 */

				this.populateRequestWithHeaders(exchange, token);
			}
			return chain.filter(exchange);
		};

	}

	public static class Config {
		// ...
	}
	/*PRIVATE*/

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}

	private boolean isAuthMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

	private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {

		ClaimDTO claimDTO = jwtHelper.verifyAccessToken(token);
		exchange.getRequest().mutate()
				.header("id", String.valueOf(claimDTO.getUserId()))
				.header("role", String.valueOf(claimDTO.getRoles()))
				.build();
	}
}
