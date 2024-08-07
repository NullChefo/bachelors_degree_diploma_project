package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.retrieve.ConnectionRetrievalDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.UserRetrieveDTO;
import com.nullchefo.socialmediaservice.entity.Connection;
import com.nullchefo.socialmediaservice.service.ConnectionService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/connection")
@Tag(name = "connections", description = "Operations related to managing connections")
@SecurityRequirement(name = "Bearer Authentication")
public class ConnectionControllerV1 {

    private final ConnectionService connectionService;

    public ConnectionControllerV1(final ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Operation(summary = "Get all connections", description = "Retrieve all connections.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connections found",
                    content = @Content(schema = @Schema(implementation = Connection.class))),
            @ApiResponse(responseCode = "404", description = "Connections not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<ConnectionRetrievalDTO>> getAllAcceptedConnectionsForCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("connectionDate"));
        Page<ConnectionRetrievalDTO> connection = connectionService.getAllConnections(pageable);

        if (connection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(connection);
    }

    @Operation(summary = "Get a connection by ID", description = "Retrieve a connection's details by ID.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connection found",
                    content = @Content(schema = @Schema(implementation = Connection.class))),
            @ApiResponse(responseCode = "404", description = "Connection not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConnectionRetrievalDTO> getConnectionById(@PathVariable Long id) {
        ConnectionRetrievalDTO connection = connectionService.getConnectionById(id);

        if (connection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(connection);
    }

    @Operation(summary = "Create a connection", description = "Create a new connection.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Send connection request")
    })
    @PostMapping("/request/{id}")
    public ResponseEntity<ConnectionRetrievalDTO> sendConnectionRequest(@PathVariable Long id) {
        connectionService.createConnectionRequest(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Create a connection", description = "Create a new connection.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accept connection request")
    })
    @PostMapping("/accept/{id}")
    public ResponseEntity<ConnectionRetrievalDTO> acceptConnectionRequest(@PathVariable Long id) {
        connectionService.acceptConnectionRequest(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Create a connection", description = "List all not accepted connection.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List not accepted connection request")
    })
    @GetMapping("/list")
    public ResponseEntity<Page<ConnectionRetrievalDTO>> listConnectionRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("connectionRequestDate").descending());

        Page<ConnectionRetrievalDTO> connection = connectionService.listNotAcceptedConnectionRequest(paging);
        return ResponseEntity.status(HttpStatus.OK).body(connection);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<Page<UserRetrieveDTO>> getSuggestionRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<UserRetrieveDTO> connection = connectionService.getSuggestionRequests(paging);

        return ResponseEntity.status(HttpStatus.OK).body(connection);
    }

    @Operation(summary = "Delete a connection by ID", description = "Delete a connection by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Connection deleted"),
            @ApiResponse(responseCode = "404", description = "Connection not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        ConnectionRetrievalDTO existingConnection = connectionService.getConnectionById(id);
        if (existingConnection == null) {
            return ResponseEntity.notFound().build();
        }
        connectionService.deleteConnection(id);
        return ResponseEntity.status(204).build();
    }
}
