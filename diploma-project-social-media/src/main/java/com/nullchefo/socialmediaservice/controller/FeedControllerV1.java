package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.retrieve.PostRetrieveDTO;
import com.nullchefo.socialmediaservice.service.FeedService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/feed")
@Tag(name = "feed", description = "Endpoints for managing feed")
@SecurityRequirement(name = "Bearer Authentication")
public class FeedControllerV1 {

    private final FeedService feedService;

    public FeedControllerV1(final FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<Page<PostRetrieveDTO>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostRetrieveDTO> posts = feedService.getFeed(paging);

        if (posts.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

}
