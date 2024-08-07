package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.HighValueUser;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighValueUserRepository extends JpaRepository<HighValueUser, Long> {
    HighValueUser findByUser(User user);
}
