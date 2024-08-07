package com.nullchefo.mailservice.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Mail {

    private String recipient;
    private Long userId;
    private String subject;
    private String body;
    private String mailType;
    private Map<String, String> mailFields;

}
