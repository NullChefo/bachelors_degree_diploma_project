package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageRetrieveDTO {
    private Long id;
    private String content;
    private UserRetrieveDTO recipient;
    private LocalDateTime creationDate;
}
