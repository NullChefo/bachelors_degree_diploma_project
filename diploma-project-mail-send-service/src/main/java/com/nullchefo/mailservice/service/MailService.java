package com.nullchefo.mailservice.service;

import com.nullchefo.mailservice.entity.Mail;
import com.nullchefo.mailservice.entity.MailDetails;

import java.io.IOException;

public interface MailService {

    // To send a simple email
    void sendSimpleMail(MailDetails details);

    // To send an email with attachment
    void sendMailWithAttachment(MailDetails details);

    void sendMail(Mail mail) throws IOException;

}
