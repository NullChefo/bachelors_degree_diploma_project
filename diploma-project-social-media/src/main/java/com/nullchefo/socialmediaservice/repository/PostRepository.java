package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Group;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //	@Cacheable(value = "postCache")
    //	Optional<Post> findById(Long postId);

    Page<Post> findAllByContentContainingIgnoreCase(String content, Pageable paging);

    // OrderByCreatedAtDesc

    Page<Post> findAllByGroup(Group group, Pageable paging);

    Page<Post> findAllByContentContainingIgnoreCaseAndGroup(
            String content,
            Group group,
            Pageable paging);

    Post findByAttachmentsId(Long id);

    Page<Post> findAllByCreatorIn(List<User> connectionsUsers, Pageable paging);

    Page<Post> findAllByCreator(User user, Pageable paging);

    List<Post> findAllByCreator(User user);

    Optional<Post> findByPostUUID(UUID postUUID);

    Page<Post> findAllByCreatorAndDeleted(User user, boolean deleted, Pageable paging);

    Page<Post> findAllByCreatorInAndDeleted(List<User> connectionsUsers, boolean deleted, Pageable paging);

    Optional<Post> findByIdAndDeleted(Long postId, boolean deleted);
}



