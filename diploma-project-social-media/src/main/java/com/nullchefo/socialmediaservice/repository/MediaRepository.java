package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByPostUUID(UUID uuid);
}
