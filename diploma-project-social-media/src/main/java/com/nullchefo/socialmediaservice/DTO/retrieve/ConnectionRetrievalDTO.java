package com.nullchefo.socialmediaservice.DTO.retrieve;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConnectionRetrievalDTO {

    private Long id;
    private UserRetrieveDTO connection;
    private LocalDateTime connectionDate;
    private boolean accepted;

}
