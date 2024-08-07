package com.nullchefo.authorizationservice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    // @ResponseBody
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication loggedInUser = securityContext.getAuthentication();
        log.trace("Logged-in user: {}", loggedInUser);
        if (loggedInUser.getPrincipal() != "anonymousUser") {
            return "redirect:https://diploma-project.nullchefo.com";
        } else {
            return "redirect:/login";
        }

    }

}
