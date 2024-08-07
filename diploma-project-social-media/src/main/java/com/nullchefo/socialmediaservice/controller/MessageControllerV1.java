package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.create.MessageCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.MessageRetrieveDTO;
import com.nullchefo.socialmediaservice.entity.Message;
import com.nullchefo.socialmediaservice.service.MessageService;
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

import java.util.List;

@RestController
@RequestMapping("/v1/message")
@Tag(name = "message", description = "Operations related to managing messages")
@SecurityRequirement(name = "Bearer Authentication")
public class MessageControllerV1 {

    private final MessageService messageService;

    public MessageControllerV1(final MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @Operation(summary = "Get all messages")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the messages",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "404", description = "Messages not found",
                    content = @Content)
    })
    public ResponseEntity<Page<MessageRetrieveDTO>> getAllMessagesForCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<MessageRetrieveDTO> messages = messageService.getAllMessages(paging);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messages);
    }

    @Operation(summary = "Get a messages by user ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the message",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "404", description = "Message not found",
                    content = @Content)
    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<MessageRetrieveDTO>> getMessagesByUserId(@PathVariable Long userId) {
        List<MessageRetrieveDTO> message = messageService.getMessagesByUserId(userId);

        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Create a new message")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Message.class)))
    })
    @PostMapping
    public ResponseEntity<MessageRetrieveDTO> createMessage(@RequestBody MessageCreateDTO message) {
        MessageRetrieveDTO messageRetrieveDTO = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageRetrieveDTO);
    }

    @Operation(summary = "Delete a message by ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Message deleted"),
            @ApiResponse(responseCode = "404", description = "Message not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        Message existingMessage = messageService.getMessageById(id);
        if (existingMessage == null) {
            return ResponseEntity.notFound().build();
        }
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
