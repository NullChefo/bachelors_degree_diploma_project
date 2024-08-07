package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.create.PostCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.PostRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.Group;
import com.nullchefo.socialmediaservice.entity.Media;
import com.nullchefo.socialmediaservice.entity.Post;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.GroupRepository;
import com.nullchefo.socialmediaservice.repository.PostRepository;
import com.nullchefo.socialmediaservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final EntityToDTOConverter entityToDTOConverter;
    private final MediaMetadataService mediaMetadataService;
    private final UserRepository userRepository;

    public PostService(
            final PostRepository postRepository,
            final GroupRepository groupRepository,
            final UserService userService,
            final EntityToDTOConverter entityToDTOConverter, final MediaMetadataService mediaMetadataService,
            final UserRepository userRepository) {
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.entityToDTOConverter = entityToDTOConverter;
        this.mediaMetadataService = mediaMetadataService;
        this.userRepository = userRepository;
    }

    public Page<PostRetrieveDTO> getAllPageable(Pageable paging) {
        User currentUser = userService.getCurrentUser();
        Page<Post> posts = postRepository.findAll(paging);
        List<PostRetrieveDTO> list = entityToDTOConverter.postsToPostsRetrievalDTO(posts.getContent(), currentUser);

        return new PageImpl<>(list, paging, posts.getTotalElements());
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostRetrieveDTO getPostRetrieveDTOById(final Long postId) {

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        return entityToDTOConverter.postToPostRetrievalDTO(post);
    }

    public PostRetrieveDTO createPost(PostCreateDTO post) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication loggedInUser = securityContext.getAuthentication();

        User user = userService.getUserByUsername(loggedInUser.getName());

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (post.getContent().length() > 500) {
            throw new ValidationFailureException("Content cannot be more then 500 symbols");
        }

        List<Media> attachments = mediaMetadataService.getMediasByUUID(post.getPostUUID());

        if (attachments == null) {
            attachments = new ArrayList<>();
        }

        Post newPost = entityToDTOConverter.postCreateDTOToPost(post, user, attachments);

        newPost.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

        Post savedPost = postRepository.save(newPost);

        return entityToDTOConverter.postToPostRetrievalDTO(savedPost);

    }

    public PostRetrieveDTO updatePost(PostRetrieveDTO post) {

        if (post.getId() == null) {
            throw new NotFoundException("Post not found");
        }

        Post existingPost = this.getPostById(post.getId());

        if (existingPost == null) {
            throw new NotFoundException("Post not found");
        }

        existingPost = this.entityToDTOConverter.postRetrieveDTOUpdatePost(existingPost, post);

        Post savedPost = postRepository.save(existingPost);
        return entityToDTOConverter.postToPostRetrievalDTO(savedPost);

    }

    public void deletePermanentlyPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        if (!Objects.equals(post.getCreator().getUsername(), userService.getCurrentUser().getUsername())) {
            throw new AuthorizationServiceException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        if (!Objects.equals(post.getCreator().getUsername(), userService.getCurrentUser().getUsername())) {
            throw new AuthorizationServiceException("You are not authorized to delete this post");
        }

        post.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
        post.setDeleted(true);
        post.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        postRepository.save(post);

    }

    public Page<PostRetrieveDTO> getAllByContentPageable(String content, Pageable paging) {
        User currentUser = userService.getCurrentUser();
        Page<Post> posts = postRepository.findAllByContentContainingIgnoreCase(content, paging);
        List<PostRetrieveDTO> list = entityToDTOConverter.postsToPostsRetrievalDTO(posts.getContent(), currentUser);

        return new PageImpl<>(list, paging, posts.getTotalElements());
    }

    public Page<PostRetrieveDTO> getAllPageableForGroup(final Pageable paging, final Long groupId) {

        Group group = groupRepository.findById(groupId).orElse(null);

        if (group == null) {
            throw new NotFoundException("Group not found");
        }
        User currentUser = userService.getCurrentUser();

        Page<Post> posts = postRepository.findAllByGroup(group, paging);

        List<PostRetrieveDTO> list = entityToDTOConverter.postsToPostsRetrievalDTO(posts.getContent(), currentUser);

        // convert list into page of type PostRetrieveDTO

        return new PageImpl<>(list, paging, posts.getTotalElements());

    }

    public Page<PostRetrieveDTO> getAllByContentPageableForGroup(
            final String content,
            final Pageable paging,
            final Long groupId) {

        Group group = groupRepository.findById(groupId).orElse(null);

        if (group == null) {
            throw new NotFoundException("Group not found");
        }

        User currentUser = userService.getCurrentUser();

        Page<Post> posts = postRepository.findAllByContentContainingIgnoreCaseAndGroup(
                content,
                group,
                paging);
        List<PostRetrieveDTO> list = entityToDTOConverter.postsToPostsRetrievalDTO(posts.getContent(), currentUser);

        return new PageImpl<>(list, paging, posts.getTotalElements());

    }

    public Post updatePostInternal(final Post post) {
        return postRepository.save(post);
    }

    public Page<PostRetrieveDTO> getAllByUsernamePageable(final String username, final Pageable paging) {

        User user = userRepository.findByUsernameIgnoreCaseAndDeleted(username, false);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        Page<Post> posts = postRepository.findAllByCreatorAndDeleted(
                user,
                false,
                paging);

        if (posts.getContent().isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        User currentUser = userService.getCurrentUser();

        List<PostRetrieveDTO> list = entityToDTOConverter.postsToPostsRetrievalDTO(posts.getContent(), currentUser);

        return new PageImpl<>(list, paging, posts.getTotalElements());

    }

}
