package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Comment;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostIdAndDeleted(Long postId, boolean deleted, Pageable paging);

    List<Comment> findAllByCreatorAndDeleted(User user, boolean deleted);

    Optional<Comment> findByIdAndDeleted(Long commentId, boolean deleted);
}
