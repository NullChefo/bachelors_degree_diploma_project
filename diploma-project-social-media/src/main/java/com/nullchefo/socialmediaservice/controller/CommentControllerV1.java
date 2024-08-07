package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.create.CommentCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.CommentRetrieveDTO;
import com.nullchefo.socialmediaservice.entity.Comment;
import com.nullchefo.socialmediaservice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comments", description = "API endpoints for managing comments")
@RestController
@RequestMapping("/v1/comment")
@SecurityRequirement(name = "Bearer Authentication")
public class CommentControllerV1 {

    private final CommentService commentService;

    public CommentControllerV1(final CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get all comments", description = "Retrieve all comments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "404", description = "Comments not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/")
    // TODO add ADMIN
    public ResponseEntity<List<CommentRetrieveDTO>> getAllComments() {
        List<CommentRetrieveDTO> comments = commentService.getAllComments();
        if (comments == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comment by ID", description = "Retrieve a comment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<CommentRetrieveDTO> getCommentById(@PathVariable Long id) {
        CommentRetrieveDTO comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsForPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CommentRetrieveDTO> comment = commentService.getCommentsByPostId(postId, paging);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Create a new comment", description = "Create a new comment.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = Comment.class)))
    })
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentCreateDTO comment) {
        CommentRetrieveDTO commentDTO;
        try {
            commentDTO = commentService.createComment(comment);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.badRequest().body(runtimeException.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @Operation(summary = "Update an existing comment", description = "Update an existing comment by its ID.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommentRetrieveDTO> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        CommentRetrieveDTO existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }
        CommentRetrieveDTO commentDTO = commentService.updateComment(comment);
        return ResponseEntity.ok(commentDTO);
    }

    @Operation(summary = "Delete a comment", description = "Delete a comment by its ID.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        CommentRetrieveDTO existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }
        commentService.deleteComment(id);
        return ResponseEntity.status(204).build();
    }
}
