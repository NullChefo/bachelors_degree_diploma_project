package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Comment;
import com.nullchefo.socialmediaservice.entity.Like;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByPostAndCreatorAndDeleted(Post post, User currentUser, boolean deleted);

    Like findByCommentAndCreatorAndDeleted(Comment comment, User currentUser, boolean deleted);

    List<Like> findAllByCreatorAndDeleted(User user, boolean deleted);

    Page<Like> findByPostAndDeleted(Post post, boolean deleted, Pageable paging);

    Page<Like> findByCommentAndDeleted(Comment comment, boolean deleted, Pageable paging);
}


