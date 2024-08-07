package com.nullchefo.socialmediaservice.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.nullchefo.socialmediaservice.entity.Group;
import com.nullchefo.socialmediaservice.entity.Media;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.GroupRepository;
import com.nullchefo.socialmediaservice.repository.MediaRepository;
import com.nullchefo.socialmediaservice.repository.PostRepository;
import com.nullchefo.socialmediaservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@Slf4j
public class MediaMetadataServiceImpl implements MediaMetadataService {
    private final GroupRepository groupRepository;

    private final AmazonS3Service amazonS3Service;
    private final MediaRepository mediaRepository;
    private final String bucketName;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final String publicPath;

    public MediaMetadataServiceImpl(
            final AmazonS3Service amazonS3Service,
            final MediaRepository mediaRepository,
            @Value("${aws.s3.bucket.name}") final String bucketName,
            @Value("${aws.s3.public-path}") final String publicPath,
            final UserRepository userRepository,
            final PostRepository postRepository,
            final GroupRepository groupRepository) {
        this.amazonS3Service = amazonS3Service;
        this.mediaRepository = mediaRepository;
        this.bucketName = bucketName;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
        this.publicPath = publicPath;
    }

    // TODO compress image
    @Override
    public String uploadForPost(MultipartFile multipartFile, String postUUID) throws IOException {

        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (multipartFile.getSize() > 10000000) {
            throw new IllegalStateException("File is too large");
        }

        List<String> allowedTypes = new ArrayList<>(Arrays.asList("image/jpeg", "image/png"));
        if (allowedTypes.contains(multipartFile.getContentType())) {
            // TODO implement
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", multipartFile.getContentType());
        metadata.put("Content-Length", String.valueOf(multipartFile.getSize()));

        UUID uuid = UUID.fromString(postUUID);

        String path = String.format("%s/%s", bucketName, uuid);
        String fileName = String.format("%s", multipartFile.getOriginalFilename());

        fileName = fileName.replaceAll(" ", "_");

        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path, fileName, Optional.of(metadata), multipartFile.getInputStream());

        // TODO check if you need to link the post
        // Saving metadata to db
        Media media = Media.builder()
                .fileName(fileName)
                .contentType(multipartFile.getContentType())
                .path(path)
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .creatorId(getCurrentUser().getId())
                .postUUID(UUID.fromString(postUUID))
                .version(putObjectResult.getMetadata()

                        .getVersionId()).build();
        mediaRepository.save(media);

        return publicPath + "/" + uuid + "/" + fileName;
    }

    @Override
    public String uploadForUser(MultipartFile multipartFile, String username) throws IOException {

        User user = getCurrentUser();

        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (multipartFile.getSize() > 10000000) {
            throw new IllegalStateException("File is too large");
        }

        List<String> allowedTypes = new ArrayList<>(Arrays.asList("image/jpeg", "image/png"));
        if (allowedTypes.contains(multipartFile.getContentType())) {
            // TODO implement
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", multipartFile.getContentType());
        metadata.put("Content-Length", String.valueOf(multipartFile.getSize()));

        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", multipartFile.getOriginalFilename());

        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path, fileName, Optional.of(metadata), multipartFile.getInputStream());

        // Saving metadata to db
        Media media = Media.builder()
                .fileName(fileName)
                .contentType(multipartFile.getContentType())
                .path(path)
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .user(user)
                .creatorId(getCurrentUser().getId())
                .version(putObjectResult.getMetadata()
                        .getVersionId()).build();
        mediaRepository.save(media);

        user.getAttachments().add(media);
        userRepository.save(user);

        return media.getPath();
    }

    @Override
    public String uploadForGroup(MultipartFile multipartFile, Long groupId) throws IOException {

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalStateException("Group not found"));

        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (multipartFile.getSize() > 10000000) {
            throw new IllegalStateException("File is too large");
        }

        List<String> allowedTypes = new ArrayList<>(Arrays.asList("image/jpeg", "image/png"));
        if (allowedTypes.contains(multipartFile.getContentType())) {
            // TODO implement
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", multipartFile.getContentType());
        metadata.put("Content-Length", String.valueOf(multipartFile.getSize()));

        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", multipartFile.getOriginalFilename());

        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path, fileName, Optional.of(metadata), multipartFile.getInputStream());

        // Saving metadata to db
        Media media = Media.builder()
                .fileName(fileName)
                .contentType(multipartFile.getContentType())
                .path(path)
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .group(group)
                .creatorId(getCurrentUser().getId())
                .version(putObjectResult.getMetadata()
                        .getVersionId()).build();

        mediaRepository.save(media);

        group.getAttachments().add(media);
        groupRepository.save(group);

        return media.getPath();
    }

    @Override
    public ResponseEntity<byte[]> download(Long id) {
        byte[] content = null;
        Media fileMeta = null;
        try {
            fileMeta = mediaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            S3Object s3Object = amazonS3Service.download(fileMeta.getPath(), fileMeta.getFileName());
            S3ObjectInputStream stream = s3Object.getObjectContent();
            content = IOUtils.toByteArray(stream);

        } catch (java.io.IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(content.length);
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileMeta.getFileName()).build());
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @Override
    public List<Media> list() {
        return new ArrayList<>(mediaRepository.findAll());
    }

    @Override
    public List<String> getImagesForPost() {
        // TODO implement
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(final Long id) {
        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with id: " + id + " is not found");
        }

        User currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new AuthorizationServiceException("User not authenticated");
        }

        if (!Objects.equals(media.getCreatorId(), currentUser.getId())) {
            throw new AuthorizationServiceException("You are not authorized to delete this file");
        }

        User user = null;
        Post post = null;
        Group group = null;

        if (media.getUser() != null) {
            user = userRepository
                    .findById(media.getUser().getId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            user.getAttachments().remove(media);
            userRepository.save(user);
        }

        if (media.getPostUUID() != null) {
            post = postRepository
                    .findByPostUUID(media.getPostUUID())
                    .orElseThrow(() -> new NotFoundException("Post not found"));
            post.getAttachments().remove(media);
            postRepository.save(post);
        }

        if (media.getGroup() != null) {
            group = groupRepository
                    .findById(media.getGroup().getId())
                    .orElseThrow(() -> new NotFoundException("Group not found"));
            group.getAttachments().remove(media);
            groupRepository.save(group);
        }

        try {
            mediaRepository.deleteById(id);
            amazonS3Service.delete(media.getPath(), media.getFileName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong!");
        }

        mediaRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<Media> getMediasByUUID(final UUID postUUID) {
        return mediaRepository.findByPostUUID(postUUID);
    }

    private User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication loggedInUser = securityContext.getAuthentication();
        return userRepository.findByUsernameIgnoreCaseAndDeleted(loggedInUser.getName(), false);
    }

}
