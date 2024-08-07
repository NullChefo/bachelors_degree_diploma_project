package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRetrieveDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String avatarURL;
    private boolean isOauth;
    private boolean verified;
    private String pronouns;
}
