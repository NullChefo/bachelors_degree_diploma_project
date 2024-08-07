package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Cacheable(value = "usersCache")
    User findByUsernameIgnoreCaseAndDeleted(String name, boolean deleted);

    Page<User> findAllByDeleted(boolean deleted, Pageable paging);

    User findByUsername(String username);

    User findByEmailIgnoreCaseAndDeleted(String email, boolean deleted);
}

