package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupRetrieveDTO {
    private Long id;
    private String name;
    private String description;
    private UserRetrieveDTO owner;
    private List<UserRetrieveDTO> members;
    private List<MediaRetrieveDTO> attachments;
    private String avatarURL;
    private Long memberCount;

}
