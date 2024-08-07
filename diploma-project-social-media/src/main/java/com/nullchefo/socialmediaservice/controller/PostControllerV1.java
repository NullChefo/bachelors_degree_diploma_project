package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.create.PostCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.PostRetrieveDTO;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.oxm.ValidationFailureException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/v1/post")
@Tag(name = "post", description = "Operations related to managing posts")
@SecurityRequirement(name = "Bearer Authentication")
public class PostControllerV1 {

    private final PostService postService;

    public PostControllerV1(final PostService postService) {
        this.postService = postService;
    }

    // http://localhost:8092/v1/posts?page=0&size=10.
    //	@GetMapping("/")
    //	@Operation(summary = "Retrieves all posts pageable")
    //	@SecurityRequirement(name = "Bearer Authentication")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
    //			@ApiResponse(responseCode = "400", description = "Posts not found"),
    //			@ApiResponse(responseCode = "401", description = "Not Authorized")
    //	})
    //	public ResponseEntity<Page<PostRetrieveDTO>> getAllPostsPageable(Pageable pageable) {
    //		Page<PostRetrieveDTO> posts = postService.getAllPageable(pageable);
    //		if (posts == null) {
    //			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //		}
    //		return ResponseEntity.ok(posts);
    //	}

    @Operation(summary = "Retrieve post for postId")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post"),
            @ApiResponse(responseCode = "400", description = "Posts not found")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostRetrieveDTO> getPostById(@PathVariable final Long postId) {

        PostRetrieveDTO post = postService.getPostRetrieveDTOById(postId);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Retrieves all posts pageable new")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
            @ApiResponse(responseCode = "400", description = "Posts not found")
    })
    @GetMapping("/")
    public ResponseEntity<Page<PostRetrieveDTO>> getAllPostsPageableNew(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String content) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostRetrieveDTO> posts;
        if (content == null) {
            posts = postService.getAllPageable(paging);
        } else {
            posts = postService.getAllByContentPageable(content, paging);
        }

        if (posts == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Retrieves posts for user pageable")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
            @ApiResponse(responseCode = "400", description = "Posts not found")
    })
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAllPostsForUsernamePageableNew(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @PathVariable final String username) {

        if (size > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The maximum page size is 20");
        }

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostRetrieveDTO> posts;

        posts = postService.getAllByUsernamePageable(username, paging);

        if (posts == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Retrieves all posts pageable for group")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
            @ApiResponse(responseCode = "400", description = "Posts not found")
    })
    @GetMapping("/group/{groupId}")
    public ResponseEntity<Page<PostRetrieveDTO>> getAllPostsForGroupPageableNew(
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String content) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostRetrieveDTO> posts;
        if (content == null) {
            posts = postService.getAllPageableForGroup(paging, groupId);
        } else {
            posts = postService.getAllByContentPageableForGroup(content, paging, groupId);
        }

        if (posts == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    @Operation(summary = "Creates a new post")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post")
    })
    public ResponseEntity<?> createPost(@RequestBody PostCreateDTO post) {
        PostRetrieveDTO newPost;
        try {
            newPost = postService.createPost(post);
        } catch (NotFoundException userNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You must be logged in to create a post");
        } catch (ValidationFailureException validationErrorException) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationErrorException.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @Operation(summary = "Updates an existing post")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostRetrieveDTO> updatePost(@PathVariable Long id, @RequestBody PostRetrieveDTO post) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }
        PostRetrieveDTO updatedPost = postService.updatePost(post);
        return ResponseEntity.ok(updatedPost);
    }

    @Operation(summary = "Deletes a post by ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
