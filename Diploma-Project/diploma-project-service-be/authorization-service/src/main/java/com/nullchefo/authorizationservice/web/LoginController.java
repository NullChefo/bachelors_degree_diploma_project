package com.nullchefo.authorizationservice.web;


import java.net.URI;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class.getSimpleName());


	@GetMapping("/login")
	// @ResponseBody
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home(Model model) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication loggedInUser = securityContext.getAuthentication();
		logger.info("Logged-in user: {}", loggedInUser);
		String userInfo = loggedInUser.toString();
		model.addAttribute("userInfo", userInfo);



//		UsernamePasswordAuthenticationToken
//		[Principal=org.springframework.security.core.userdetails.User
//		[Username=chefo, Password=[PROTECTED], Enabled=true, AccountNonExpired=true,
//		credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[USER]],
//		Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails
//		[RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=585D46B1F4259BEE81F38172998BAAA5],
//		Granted Authorities=[USER]]

		if (loggedInUser.getPrincipal() != "anonymousUser") {
			return "home";
		} else {
			return "redirect:/login";
		}

	}




}
