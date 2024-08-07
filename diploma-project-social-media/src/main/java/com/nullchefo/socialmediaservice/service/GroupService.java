package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.create.GroupCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.GroupRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.Group;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    private final UserService userService;

    private final EntityToDTOConverter entityToDTOConverter;

    public GroupService(
            final GroupRepository groupRepository, final UserService userService,
            final EntityToDTOConverter entityToDTOConverter) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.entityToDTOConverter = entityToDTOConverter;
    }

    public List<GroupRetrieveDTO> getAllGroups() {
        User user = userService.getCurrentUser();
        List<Group> groups = groupRepository.findAllByMembersContaining(user);

        return entityToDTOConverter.groupsToGroupsRetrieveDTO(groups);
    }

    public GroupRetrieveDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found");
        }
        return entityToDTOConverter.groupToGroupRetrieveDTO(group);
    }

    public void createGroup(GroupCreateDTO group) {
        User currentUser = userService.getCurrentUser();

        Group newGroup = Group.builder()
                .name(group.getName())
                .owner(currentUser)
                .description(group.getDescription())
                .members(new ArrayList<>(List.of(currentUser)))
                .memberCount(1L)
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        groupRepository.save(newGroup);
    }

    public void updateGroup(GroupRetrieveDTO group) {
        User currentUser = userService.getCurrentUser();
        Group groupToUpdate = groupRepository.findById(group.getId()).orElse(null);

        if (groupToUpdate == null) {
            throw new NotFoundException("Group not found");
        }
        if (groupToUpdate.getOwner() != currentUser) {
            throw new AuthorizationServiceException("You are not the owner of this group");
        }

        groupToUpdate.setName(group.getName());
        groupToUpdate.setDescription(group.getDescription());
        groupToUpdate.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        groupRepository.save(groupToUpdate);
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            throw new NotFoundException("Group not found");
        }
        User user = userService.getCurrentUser();

        if (group.getOwner() != user) {
            throw new AuthorizationServiceException("You are not the owner of this group");
        }
        groupRepository.deleteById(id);
    }

    public void addUserToGroup(Long userId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));
        if (group != null) {
            User user = userService.getUserById(userId);
            group.getMembers().add(user);
            groupRepository.save(group);
        } else {
            throw new NotFoundException("Group not found");
        }

    }

    public void removeUserFromGroup(Long userId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Group not found"));
        User currentUser = userService.getCurrentUser();

        if (group == null) {
            throw new NotFoundException("Group not found");
        }

        if (group.getOwner() != currentUser) {
            throw new AuthorizationServiceException("You are not the owner of this group");
        }
        User user = userService.getUserById(userId);
        List<User> members = group.getMembers();

        members.remove(user);
        group.setMembers(members);
        groupRepository.save(group);

    }

    public Page<GroupRetrieveDTO> findByName(final String name, final Pageable paging) {

        Page<Group> groups = groupRepository.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(name, paging);

        if (groups.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<GroupRetrieveDTO> list = entityToDTOConverter.groupsToGroupsRetrieveDTO(groups.getContent());

        return new PageImpl<>(list, paging, groups.getTotalElements());
    }

    public Page<GroupRetrieveDTO> findGroupsForCurrentUser(final Pageable paging) {
        User user = userService.getCurrentUser();
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        Page<Group> groups = groupRepository.findByMembersContaining(user, paging);

        if (groups.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }

        List<GroupRetrieveDTO> list = entityToDTOConverter.groupsToGroupsRetrieveDTO(groups.getContent());

        return new PageImpl<>(list, paging, groups.getTotalElements());

    }
}
