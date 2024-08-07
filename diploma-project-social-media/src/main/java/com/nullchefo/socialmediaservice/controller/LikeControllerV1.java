package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.retrieve.LikeRetrieveDTO;
import com.nullchefo.socialmediaservice.service.LikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/v1/like")
@Tag(name = "like", description = "Operations related to managing likes")
@SecurityRequirement(name = "Bearer Authentication")
public class LikeControllerV1 {

    private final LikeService likeService;

    public LikeControllerV1(final LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<LikeRetrieveDTO> likeDislikePost(@PathVariable final String postId) {
        LikeRetrieveDTO likeDTO = likeService.likeDislikePost(Long.parseLong(postId));
        return ResponseEntity.ok(likeDTO);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getLikesForPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<LikeRetrieveDTO> likesDTO;
        try {
            likesDTO = likeService.getLikesForPostId(postId, paging);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }

        if (likesDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(likesDTO);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> getLikesForComment(
            @PathVariable Long commentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<LikeRetrieveDTO> likeRetrieveDTO;
        try {
            likeRetrieveDTO = likeService.getLikesForCommentId(commentId, paging);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }

        if (likeRetrieveDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(likeRetrieveDTO);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<LikeRetrieveDTO> likeDislikeComment(@PathVariable final String commentId) {
        LikeRetrieveDTO likeDTO = likeService.likeDislikeComment(Long.parseLong(commentId));
        return ResponseEntity.ok(likeDTO);
    }

}
