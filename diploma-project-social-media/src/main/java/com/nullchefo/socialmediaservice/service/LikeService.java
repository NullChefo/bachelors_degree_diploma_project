package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.retrieve.LikeRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.Comment;
import com.nullchefo.socialmediaservice.entity.Like;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.CommentRepository;
import com.nullchefo.socialmediaservice.repository.LikeRepository;
import com.nullchefo.socialmediaservice.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;
    private final EntityToDTOConverter entityToDTOConverter;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public LikeService(
            final LikeRepository likeRepository, final PostService postService, final CommentService commentService,
            final UserService userService, final EntityToDTOConverter entityToDTOConverter,
            final PostRepository postRepository,
            final CommentRepository commentRepository) {
        this.likeRepository = likeRepository;
        this.postService = postService;

        this.commentService = commentService;
        this.userService = userService;
        this.entityToDTOConverter = entityToDTOConverter;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public LikeRetrieveDTO likeDislikePost(Long postId) {
        Post post = postService.getPostById(postId);

        User user = userService.getCurrentUser();

        Like oldLike = likeRepository.findByPostAndCreatorAndDeleted(post, user, false);

        if (post.getLikeCount() == null) {
            post.setLikeCount(0L);
        }

        if (oldLike != null) {

            post.getLikes().remove(oldLike);
            post.setLikes(post.getLikes());

            post.setLikeCount(post.getLikeCount() - 1);

            postService.updatePostInternal(post);

            likeRepository.delete(oldLike);

            return entityToDTOConverter.likeToLikeRetrieveDTO(oldLike, user);
        }

        Like newLike = Like.builder()
                .post(post)
                .creator(userService.getCurrentUser())
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        newLike = likeRepository.save(newLike);
        post.getLikes().add(newLike);
        post.setLikeCount(post.getLikeCount() + 1);

        postService.updatePostInternal(post);

        return entityToDTOConverter.likeToLikeRetrieveDTO(newLike, user);

    }

    public LikeRetrieveDTO likeDislikeComment(final Long likeId) {
        Comment comment = commentService.getCommentEntityById(likeId);

        User user = userService.getCurrentUser();

        Like oldLike = likeRepository.findByCommentAndCreatorAndDeleted(comment, userService.getCurrentUser(), false);

        if (comment.getLikeCount() == null) {
            comment.setLikeCount(0L);
        }

        if (oldLike != null) {

            comment.getLikes().remove(oldLike);
            comment.setLikes(comment.getLikes());
            comment.setLikeCount(comment.getLikeCount() - 1);

            commentService.updateComment(comment);
            likeRepository.delete(oldLike);
            return entityToDTOConverter.likeToLikeRetrieveDTO(oldLike, user);
        }
        Like newLike = Like.builder()
                .comment(comment)
                .creator(userService.getCurrentUser())
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        newLike = likeRepository.save(newLike);
        comment.getLikes().add(newLike);
        comment.setLikeCount(comment.getLikeCount() + 1);

        commentService.updateComment(comment);

        return entityToDTOConverter.likeToLikeRetrieveDTO(newLike, user);

    }

    public Page<LikeRetrieveDTO> getLikesForPostId(final Long postId, final Pageable paging) {
        Post post = postService.getPostById(postId);

        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        Page<Like> likes = likeRepository.findByPostAndDeleted(post, false, paging);

        //remove
        post.setLikeCount(likes.getTotalElements());
        postRepository.save(post);

        if (likes == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<LikeRetrieveDTO> likeRetrieveDTOs = entityToDTOConverter.convertLikesToLikesRetrieveDTO(likes.getContent());

        return new PageImpl<>(likeRetrieveDTOs, paging, likes.getTotalElements());

    }

    public Page<LikeRetrieveDTO> getLikesForCommentId(final Long commentId, final Pageable paging) {

        Comment comment = commentService.getCommentEntityById(commentId);

        if (comment == null) {
            throw new NotFoundException("Comment not found");
        }

        Page<Like> likes = likeRepository.findByCommentAndDeleted(comment, false, paging);
        if (likes == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        //remove
        comment.setLikeCount(likes.getTotalElements());
        commentRepository.save(comment);

        List<LikeRetrieveDTO> likeRetrieveDTOs = entityToDTOConverter.convertLikesToLikesRetrieveDTO(likes.getContent());

        return new PageImpl<>(likeRetrieveDTOs, paging, likes.getTotalElements());

    }
}
