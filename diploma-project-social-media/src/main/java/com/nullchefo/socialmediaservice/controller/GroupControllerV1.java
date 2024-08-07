package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.create.GroupCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.GroupRetrieveDTO;
import com.nullchefo.socialmediaservice.entity.Group;
import com.nullchefo.socialmediaservice.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/v1/group")
@Tag(name = "Groups", description = "Endpoints for managing groups")
@SecurityRequirement(name = "Bearer Authentication")
public class GroupControllerV1 {
    private final GroupService groupService;

    public GroupControllerV1(final GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Get all groups", description = "Retrieve all groups")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groups found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Groups not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<GroupRetrieveDTO>> getAllGroups() {
        List<GroupRetrieveDTO> groups = groupService.getAllGroups();
        if (groups == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    @Operation(summary = "Get all groups for user", description = "Retrieve all groups for user")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groups found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Groups not found", content = @Content)
    })
    @GetMapping("/my-groups")
    public ResponseEntity<Page<GroupRetrieveDTO>> getAllGroupsForUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<GroupRetrieveDTO> groups = groupService.findGroupsForCurrentUser(paging);
        if (groups == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    @Operation(summary = "Get all groups", description = "Retrieve all groups")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Groups found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Groups not found", content = @Content)
    })
    @GetMapping("/name")
    public ResponseEntity<Page<GroupRetrieveDTO>> findByName(
            @RequestBody String name, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<GroupRetrieveDTO> groups = groupService.findByName(name, paging);
        if (groups == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    @Operation(summary = "Get group by ID", description = "Retrieve a group by its unique ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GroupRetrieveDTO> getGroupById(@PathVariable Long id) {
        GroupRetrieveDTO group = groupService.getGroupById(id);
        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(group);
    }

    @Operation(summary = "Create group", description = "Create a new group")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            })
    })
    @PostMapping
    public ResponseEntity<GroupRetrieveDTO> createGroup(@RequestBody GroupCreateDTO group) {
        groupService.createGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Add use to group", description = "Add use to group")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/user")
    public ResponseEntity<?> addUserToGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        try {
            groupService.addUserToGroup(userId, groupId);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Add use to group", description = "Add use to group")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/user")
    public ResponseEntity<?> removeUserFromGroup(@RequestParam Long userId, @RequestParam Long groupId) {

        try {
            groupService.removeUserFromGroup(userId, groupId);
        }
        //404
        catch (NotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
        }
        // 400
        catch (AuthorizationServiceException notAuthorizedException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(notAuthorizedException.getMessage());
        }
        // 500
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update group", description = "Update an existing group")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Group updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))
            }),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GroupRetrieveDTO> updateGroup(@PathVariable Long id, @RequestBody GroupRetrieveDTO group) {
        GroupRetrieveDTO existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        groupService.updateGroup(group);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete group", description = "Delete an existing group")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Group not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        GroupRetrieveDTO existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

}
