package com.nullchefo.mailservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {
        @Server(url = "https://backend.nullchefo.com/mail-send"), @Server(url = "http://localhost:8000/mail-send"),
        @Server(url = "http://localhost:8083")}, info = @Info(title = "Mail Send Service APIs", version = "v0.0.1", description = "Mail Send Service API", contact = @Contact(url = "https://nullchefo.com", name = "Stefan", email = "stefank.dev.acc@gmail.com")))
/*
@SecurityScheme(
		name = "security_auth",
		type = SecuritySchemeType.OAUTH2,
		in = SecuritySchemeIn.HEADER,
		bearerFormat = "jwt",
		flows = @OAuthFlows(
				authorizationCode = @OAuthFlow(
						// https://auth.nullchefo.com/oauth2/authorize
						authorizationUrl = "http://authorization-server:9000/oauth2/authorize",
						// https://auth.nullchefo.com/oauth2/authorize
						tokenUrl = "http://authorization-server:9000/oauth2/token",
						scopes = {
								@OAuthScope(name = "openid", description = "openid scope"),
									@OAuthScope(name = "mail_send.read", description = "with this scope, the user can access every get request"),
									@OAuthScope(name = "mail_send.write", description = "with this scope, the user can access every post, put and delete request")
						}
				)
		)
)

 */
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPI3Config {
}
