package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.retrieve.PostRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.*;
import com.nullchefo.socialmediaservice.repository.ConnectionRepository;
import com.nullchefo.socialmediaservice.repository.GroupRepository;
import com.nullchefo.socialmediaservice.repository.HighValueUserRepository;
import com.nullchefo.socialmediaservice.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final GroupRepository groupRepository;

    private final ConnectionRepository connectionRepository;

    private final HighValueUserRepository highValueUserRepository;

    private final EntityToDTOConverter entityToDTOConverter;

    public FeedService(
            final PostRepository postRepository,
            final UserService userService,
            final GroupRepository groupRepository, final ConnectionRepository connectionRepository,
            final HighValueUserRepository highValueUserRepository, final EntityToDTOConverter entityToDTOConverter) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.groupRepository = groupRepository;
        this.connectionRepository = connectionRepository;

        this.highValueUserRepository = highValueUserRepository;
        this.entityToDTOConverter = entityToDTOConverter;
    }

    // TODO heavy optimization
    public Page<PostRetrieveDTO> getFeed(final Pageable paging) {
        // get current users
        User currentUser = userService.getCurrentUser();

        ArrayList<Post> allPosts = new ArrayList<>();

        // get connections
        List<Connection> connectionsList = connectionRepository.findAllByUserAndAcceptedAndDeleted(
                currentUser,
                true,
                false);

        // get groups
        List<Group> groups = groupRepository.findAllByMembersContaining(currentUser);

        // post for connections
        List<User> connectionsUsers = new ArrayList<>();
        connectionsList.parallelStream().forEach(c -> {
            connectionsUsers.add(c.getUser());
        });

        Page<Post> postsForConnections = postRepository.findAllByCreatorInAndDeleted(connectionsUsers, false, paging);
        if (!postsForConnections.getContent().isEmpty()) {
            postsForConnections.getContent().parallelStream().forEach(p -> {
                if (!allPosts.contains(p)) {
                    allPosts.add(p);
                }
            });
        }
        // post for groups
        List<User> groupUsers = new ArrayList<>();
        groups.parallelStream().forEach(g -> {
            groupUsers.add(g.getOwner());
        });
        Page<Post> postsForGroups = postRepository.findAllByCreatorInAndDeleted(groupUsers, false, paging);
        if (!postsForGroups.getContent().isEmpty()) {
            postsForGroups.getContent().parallelStream().forEach(p -> {
                if (!allPosts.contains(p)) {
                    allPosts.add(p);
                }
            });
        }

        // post for highValueUsers
        List<User> userList = new ArrayList<>();
        List<HighValueUser> highValueUsers = highValueUserRepository.findAll();
        if (!highValueUsers.isEmpty()) {
            highValueUsers.parallelStream().forEach(g -> {
                        if (g.isActive()) {
                            userList.add(g.getUser());
                        }
                    }

            );
            Page<Post> postsForHighValueUsers = postRepository.findAllByCreatorInAndDeleted(userList, false, paging);
            System.out.printf(postsForHighValueUsers.toString());
            if (!postsForHighValueUsers.getContent().isEmpty()) {
                postsForHighValueUsers.getContent().parallelStream().forEach(p -> {
                    if (!allPosts.contains(p)) {
                        allPosts.add(p);
                    }
                });
            }
        }

        if (allPosts.size() < 30) {
            Page<Post> postsForCurrentUser = postRepository.findAllByCreatorAndDeleted(currentUser, false, paging);
            if (!postsForCurrentUser.getContent().isEmpty()) {
                postsForCurrentUser.getContent().parallelStream().forEach(p -> {
                    if (!allPosts.contains(p)) {
                        allPosts.add(p);
                    }
                });
            }
        }

        // sort the ArrayList for newest

        ArrayList<Post> sortedPost = new ArrayList<>();

        if (!allPosts.isEmpty()) {
            sortedPost = allPosts.stream()
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // convert to DTO
        List<PostRetrieveDTO> postRetrieveDTOs = entityToDTOConverter.postsToPostsRetrievalDTO(sortedPost, currentUser);

        // convert to Page
        int pageNumber = 0; // the page number, starting from 0
        int pageSize = 30; // the number of items per page
        return new PageImpl<>(postRetrieveDTOs, PageRequest.of(pageNumber, pageSize), postRetrieveDTOs.size());
    }

}
