package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.entity.Media;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MediaMetadataService {
    String uploadForPost(MultipartFile file, String postUUID) throws IOException;

    String uploadForUser(MultipartFile file, String username) throws IOException;

    String uploadForGroup(MultipartFile file, Long groupId) throws IOException;

    ResponseEntity<byte[]> download(Long id);

    List<Media> list();

    List<String> getImagesForPost();

    ResponseEntity<?> delete(Long id);

    List<Media> getMediasByUUID(UUID postUUID);
}
