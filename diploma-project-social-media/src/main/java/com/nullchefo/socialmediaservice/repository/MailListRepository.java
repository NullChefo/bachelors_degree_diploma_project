package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.MailList;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailListRepository extends JpaRepository<MailList, Long> {
    MailList findByUser(User user);
}
