package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.create.CommentCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.CommentRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.Comment;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.CommentRepository;
import com.nullchefo.socialmediaservice.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    private final PostRepository postRepository;

    private final EntityToDTOConverter entityToDTOConverter;

    public CommentService(
            final CommentRepository commentRepository,
            final UserService userService,
            final PostRepository postRepository, final EntityToDTOConverter entityToDTOConverter) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postRepository = postRepository;
        this.entityToDTOConverter = entityToDTOConverter;
    }

    public List<CommentRetrieveDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();

        if (comments == null) {
            return new ArrayList<>();
        }

        List<CommentRetrieveDTO> commentRetrieveDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            commentRetrieveDTOs.add(commentRetrieveDTO(comment));
        }
        return commentRetrieveDTOs;
    }

    public CommentRetrieveDTO getCommentById(Long id) {

        Comment comment = commentRepository.findById(id).orElse(null);
        return commentRetrieveDTO(comment);
    }

    public Page<CommentRetrieveDTO> getCommentsByPostId(final Long postId, final Pageable paging) {
        Post post = postRepository.findByIdAndDeleted(postId, false).orElse(null);
        if (post == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        Page<Comment> comments = commentRepository.findByPostIdAndDeleted(postId, false, paging);
        User currentUser = userService.getCurrentUser();

        if (comments == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        // remove
        post.setCommentCount(comments.getTotalElements());
        postRepository.save(post);

        // return entityToDTOConverter.convertPageOfCommentsIntoPageOfCommentRetrieveDTO(comments);

        List<CommentRetrieveDTO> list = entityToDTOConverter.commentsToCommentsRetrieveDTO(
                comments.getContent(),
                currentUser);

        if (list == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        return new PageImpl<>(list, paging, comments.getTotalElements());

    }

    public CommentRetrieveDTO createComment(CommentCreateDTO comment) {
        User user = userService.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User  not found");
        }

        Post post = postRepository.findById(comment.getPostId()).orElse(null);

        if (post == null) {
            throw new RuntimeException("Post not found");
        }

        Comment newComment = commentToCommentCreateDTO(comment, user, post);
        newComment.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

        try {
            newComment = commentRepository.save(newComment);
        } catch (Exception e) {
            throw new RuntimeException("Comment too long");
        }

        post.getComments().add(newComment);
        if (post.getCommentCount() == null) {
            post.setCommentCount(1L);
        } else {
            post.setCommentCount(post.getCommentCount() + 1);
        }

        postRepository.save(post);

        return CommentRetrieveDTO
                .builder()
                .content(newComment.getContent())
                .creator(entityToDTOConverter.convertUserToUserRetrieveDTO(user))
                .build();
    }

    public CommentRetrieveDTO updateComment(Comment comment) {
        commentRepository.save(comment);
        return commentRetrieveDTO(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public CommentRetrieveDTO commentRetrieveDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        return CommentRetrieveDTO.builder().content(comment.getContent()).build();

    }

    public Comment commentToCommentCreateDTO(CommentCreateDTO commentCreateDTO, User user, Post post) {
        return Comment.builder().content(commentCreateDTO.getContent()).post(post).creator(user).build();

    }

    public Comment getCommentEntityById(final Long commentId) {
        return commentRepository.findByIdAndDeleted(commentId, false).orElse(null);
    }

}
