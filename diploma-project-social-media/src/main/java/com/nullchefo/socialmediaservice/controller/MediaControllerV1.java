package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.service.MediaMetadataService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;

@RestController
@RequestMapping("/v1/media")
@Tag(name = "media", description = "Operations related to managing media")
@SecurityRequirement(name = "Bearer Authentication")
public class MediaControllerV1 {

    private final MediaMetadataService mediaMetadataService;

    public MediaControllerV1(final MediaMetadataService mediaMetadataService) {
        this.mediaMetadataService = mediaMetadataService;
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileForPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("postUUID") String postUUID) {
        String url = null;
        try {
            url = mediaMetadataService.uploadForPost(file, postUUID);
        } catch (IOException exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exited with exception: " + exception);
        }

        return ResponseEntity.ok().body(url);
    }

    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFileForProfile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username) {
        String url = null;

        try {
            url = mediaMetadataService.uploadForUser(file, username);
        } catch (IOException exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exited with exception: " + exception);
        }

        return ResponseEntity.ok().body(url);
    }

    @PostMapping(value = "/group", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFileForGroup(
            @RequestParam("file") MultipartFile file,
            @RequestParam("groupId") Long groupId) {
        String url = null;

        try {
            url = mediaMetadataService.uploadForGroup(file, groupId);
        } catch (IOException exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exited with exception: " + exception);
        }

        return ResponseEntity.ok().body(url);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return mediaMetadataService.download(id);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return mediaMetadataService.delete(id);
        } catch (AuthorizationServiceException unauthenticatedException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(unauthenticatedException.getMessage());
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
        }

    }

}
