package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostRetrieveDTO {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private UserRetrieveDTO creator;

    //	private List<CommentRetrieveDTO> comments;
    //	private List<LikeRetrieveDTO> likes;

    private Long likeCount;

    private Long commentCount;

    private boolean isLiked;
    private boolean isCommented;

    private List<MediaRetrieveDTO> attachments;

}
