package com.stefan.gatewayservice.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.stefan.gatewayservice.dto.ClaimDTO;

@Component
public class RemoteAuthenticationFactory extends AbstractGatewayFilterFactory<RemoteAuthenticationFactory.Config> {

	private final WebClient.Builder webClientBuilder;

	public RemoteAuthenticationFactory(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClientBuilder = webClientBuilder;
	}



	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {

/*
			ServerHttpRequest request = exchange.getRequest();

			String authHeaders = this.getAuthHeader(request);

			System.out.printf(authHeaders);

			if (!authHeaders.equals("")) {

				System.out.println( "auth header " + this.getAuthHeader(request));


				if (this.isAuthMissing(request)) {
					return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
				}

				String authHeader = this.getAuthHeader(request);


				String token = authHeader.replace("Bearer ", "");


				// TODO make the request to the auth service


				// /api/auth/v1/validate/{token}


				// cloud gateway routes address it id auth-service-route

				boolean isTokenValid;

				// TODO change to reactive or to gRPC

				Mono<?> response = client.get()
												   .uri("/auth/v1/validate/{token}", token)
												   .retrieve()
												   .onStatus(httpStatus -> httpStatus.value() == 401, clientResponse -> Mono.empty() isTokenValid = true )
												   .toBodilessEntity();

				response.subscribe(System.out::println);

				System.out.println();

			 */

			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				throw new RuntimeException("Missing authorization information");
			}

			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

			String[] parts = authHeader.split(" ");

			if (parts.length != 2 || !"Bearer".equals(parts[0])) {
				throw new RuntimeException("Incorrect authorization structure");
			}

			return webClientBuilder.build()
								   .post()
								   //  .uri("http://auth-service/auth/v1/validate/" + parts[1])
								   .uri("http://localhost:8086/auth/v1/validate/" + parts[1])
								   .retrieve().bodyToMono(ClaimDTO.class)
								   .map(claimDTO -> {
									   exchange.getRequest()
											   .mutate()
											   .header("id", String.valueOf(claimDTO.getUserId()))
											   .header("role", String.valueOf(claimDTO.getRoles()));
									   return exchange;
								   }).flatMap(chain::filter);



		};

	}
	public static class Config {
		// ...
	}

}
