package com.nullchefo.mailservice.repository;

import com.nullchefo.mailservice.entity.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailStatusRepository extends JpaRepository<MailStatus, Long> {
    void deleteByUserId(Long userId);
}
