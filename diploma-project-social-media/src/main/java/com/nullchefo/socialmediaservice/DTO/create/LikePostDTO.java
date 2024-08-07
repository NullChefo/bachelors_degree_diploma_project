package com.nullchefo.socialmediaservice.DTO.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LikePostDTO {

    @NotNull
    private Long postId;

}
