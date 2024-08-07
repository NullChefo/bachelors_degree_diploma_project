package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeRetrieveDTO {

    private Long id;
    private UserRetrieveDTO user;
}
