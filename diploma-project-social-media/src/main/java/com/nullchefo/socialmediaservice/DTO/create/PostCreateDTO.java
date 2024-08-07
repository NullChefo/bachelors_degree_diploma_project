package com.nullchefo.socialmediaservice.DTO.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PostCreateDTO {

    // generate in frontend
    @NotNull
    private UUID postUUID;
    @NotNull
    private String content;
    @NotNull
    private Long groupId;
}
