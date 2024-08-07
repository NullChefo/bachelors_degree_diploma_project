package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.retrieve.ConnectionRetrievalDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.UserRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.Connection;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.ConnectionRepository;
import com.nullchefo.socialmediaservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final EntityToDTOConverter entityToDTOConverter;

    private final UserService userService;
    private final UserRepository userRepository;

    public ConnectionService(
            final ConnectionRepository connectionRepository,
            final EntityToDTOConverter entityToDTOConverter,
            final UserService userService,
            final UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.entityToDTOConverter = entityToDTOConverter;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Page<ConnectionRetrievalDTO> getAllConnections(final Pageable paging) {

        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        Page<Connection> connections = connectionRepository.findAllByUserAndAcceptedAndDeletedOrderByConnectionDateDesc(
                currentUser,
                true,
                false,
                paging);

        if (!connections.isEmpty()) {
            currentUser.setConnectionsCount(connections.getTotalElements());
            userRepository.save(currentUser);
        }

        List<ConnectionRetrievalDTO> list = entityToDTOConverter.connectionsToConnectionRetrievalDTO(connections.getContent());

        //return entityToDTOConverter.listToPage(paging, list);

        if (list == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        return new PageImpl<>(list, paging, connections.getTotalElements());

    }

    public List<ConnectionRetrievalDTO> getConnectionsOfUser(Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return new ArrayList<>();
        }

        List<Connection> connections = connectionRepository.findAllByUserAndAcceptedAndDeleted(user, true, false);
        return entityToDTOConverter.friendsToFriendsRetrievalDTO(connections);

    }

    public ConnectionRetrievalDTO createConnectionRequest(Long userId) {
        User currentUser = userService.getCurrentUser();
        User user = userService.getUserById(userId);

        Connection connection = Connection
                .builder()
                .user(currentUser)
                .connection(user)
                .accepted(false)
                .connectionRequestDate(
                        LocalDateTime.now(ZoneOffset.UTC))
                .connectionDate(null)
                .build();

        return entityToDTOConverter.friendToFriendRetrievalDTO(connectionRepository.save(connection));
    }

    public void acceptConnectionRequest(Long userId) {
        // changed spots
        User currentUser = userService.getUserById(userId);
        User user = userService.getCurrentUser();

        Connection connection = connectionRepository.findByUserAndConnectionAndDeleted(currentUser, user, false);

        connection.setAccepted(true);
        connection.setConnectionDate(LocalDateTime.now(ZoneOffset.UTC));

        connectionRepository.save(connection);
    }

    public Page<ConnectionRetrievalDTO> listNotAcceptedConnectionRequest(final Pageable paging) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        Page<Connection> connections = connectionRepository.findAllByUserAndAcceptedAndDeletedOrderByConnectionRequestDateDesc(
                currentUser,
                false,
                false,
                paging);

        List<ConnectionRetrievalDTO> list = entityToDTOConverter.connectionsToConnectionRetrievalDTO(connections.getContent());

        return new PageImpl<>(list, paging, connections.getTotalElements());

    }

    public void deleteConnection(Long id) {
        connectionRepository.deleteById(id);
    }

    public ConnectionRetrievalDTO getConnectionById(final Long id) {
        Connection connection = connectionRepository.findById(id).orElse(null);
        return entityToDTOConverter.friendToFriendRetrievalDTO(connection);
    }

    public Page<UserRetrieveDTO> getSuggestionRequests(final Pageable paging) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<User> users = userService.getAllNewUsers(paging).getContent();

        if (users.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }

        users.remove(currentUser);

        List<User> currentUserConnectionsUsers = currentUser.getConnection().parallelStream().
                map(Connection::getConnection).
                toList();
        // remove the existing connections
        users.removeAll(currentUserConnectionsUsers);

        if (users.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<UserRetrieveDTO> list = entityToDTOConverter.usersToUsersRetrieveDTO(users);

        return new PageImpl<>(list, paging, currentUserConnectionsUsers.size());
    }
}


