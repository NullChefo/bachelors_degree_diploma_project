package com.nullchefo.mailservice.service;

import com.nullchefo.mailservice.entity.MailStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MailStatusService {
    void saveStatus(MailStatus mailStatus);

    ResponseEntity<?> removeUser(Long userId);

    List<MailStatus> getAllUsers();
}
