package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.retrieve.HighValueUserRetrieveDTO;
import com.nullchefo.socialmediaservice.service.HighValueUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/high-value-users")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@Tag(name = "high-value-users", description = "Operations related to managing high value users")
@SecurityRequirement(name = "Bearer Authentication")
public class HighValueUsersControllerV1 {

    private final HighValueUserService highValueUserService;

    public HighValueUsersControllerV1(final HighValueUserService highValueUserService) {
        this.highValueUserService = highValueUserService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<HighValueUserRetrieveDTO>> getHighValueUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<HighValueUserRetrieveDTO> highValueUsers = highValueUserService.getHighValueUsers(paging);

        if (highValueUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(highValueUsers);

    }

    @PostMapping("/{userId}")
    public ResponseEntity<HighValueUserRetrieveDTO> createHighValueUsers(@PathVariable final Long userId) {
        HighValueUserRetrieveDTO userRetrieveDTO = highValueUserService.createHighValueUser(userId);

        if (userRetrieveDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userRetrieveDTO);
    }

}
