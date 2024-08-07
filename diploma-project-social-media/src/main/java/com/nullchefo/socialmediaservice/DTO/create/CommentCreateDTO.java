package com.nullchefo.socialmediaservice.DTO.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreateDTO {
    @NotNull
    private String content;
    @NotNull
    private Long postId;

}
