package com.nullchefo.mailservice.contoller;

import com.nullchefo.mailservice.entity.MailStatus;
import com.nullchefo.mailservice.service.MailStatusService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mail-status")
@SecurityRequirement(name = "Bearer Authentication")
public class MailStatusRestController {

    private final MailStatusService mailStatusService;

    public MailStatusRestController(final MailStatusService mailStatusService) {
        this.mailStatusService = mailStatusService;
    }

    @GetMapping("/")
    public ResponseEntity<List<MailStatus>> getAllUsers() {
        return ResponseEntity.ok(mailStatusService.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeUserFromMailList(@PathVariable final Long userId) {
        return mailStatusService.removeUser(userId);
    }

}
