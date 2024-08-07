package com.nullchefo.socialmediaservice.DTO.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MediaCreateDTO {
    @NotNull
    private UUID postUUID;
}
