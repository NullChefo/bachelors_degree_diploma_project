package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentRetrieveDTO {

    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private UserRetrieveDTO creator;
    //	private List<LikeRetrieveDTO> likes;
    private Long likeCount;
    private String retrieve;
    private boolean isLiked;
}
