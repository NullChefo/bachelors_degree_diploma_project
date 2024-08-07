package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Message;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllBySenderAndDeleted(User currentUser, boolean deleted, Pageable paging);

    List<Message> findAllBySenderAndRecipientOrderByCreatedAtDesc(User sender, User recipient);

    List<Message> findAllBySenderOrRecipientAndDeleted(User user, User user1, boolean deleted);
}


