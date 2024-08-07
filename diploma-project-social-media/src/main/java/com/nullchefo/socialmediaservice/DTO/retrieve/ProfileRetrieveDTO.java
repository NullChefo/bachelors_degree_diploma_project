package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProfileRetrieveDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String avatarURL;
    private Long connectionsCount;
    private List<MediaRetrieveDTO> attachments;
    private String work;
    private String university;
    private boolean isOauth;
    private String school;
    private String gender;
    private String phone;
    private String about;
    private String websiteURL;
    private String linkedInURL;
    private boolean verified;
    private LocalDateTime verifiedAt;
    private String pronouns;
}
