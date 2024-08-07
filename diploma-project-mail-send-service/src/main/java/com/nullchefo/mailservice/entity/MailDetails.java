package com.nullchefo.mailservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    public MailDetails(Mail mail) {
        this.recipient = mail.getRecipient();
        this.msgBody = mail.getBody();
        this.subject = mail.getSubject();
    }

}
