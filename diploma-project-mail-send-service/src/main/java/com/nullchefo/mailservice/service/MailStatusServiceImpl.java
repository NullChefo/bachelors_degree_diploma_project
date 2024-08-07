package com.nullchefo.mailservice.service;

import com.nullchefo.mailservice.entity.MailStatus;
import com.nullchefo.mailservice.repository.MailStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailStatusServiceImpl implements MailStatusService {

    private final MailStatusRepository mailStatusRepository;

    public MailStatusServiceImpl(final MailStatusRepository mailStatusRepository) {
        this.mailStatusRepository = mailStatusRepository;
    }

    public void saveStatus(final MailStatus mailStatus) {
        mailStatusRepository.save(mailStatus);

    }

    @Override
    public ResponseEntity<?> removeUser(final Long userId) {

        try {
            mailStatusRepository.deleteByUserId(userId);
        } catch (Exception e) {
            return ResponseEntity.status(409).body("There is a problem removing user from mail status list");
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<MailStatus> getAllUsers() {
        return mailStatusRepository.findAll();
    }
}
