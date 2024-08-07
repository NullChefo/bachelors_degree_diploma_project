package com.nullchefo.socialmediaservice.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MailDTO {

    private String recipient;
    private Long userId;
    private String subject;
    private String body;
    private String mailType;
    private Map<String, String> mailFields;

}
