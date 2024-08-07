package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Group;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByNameContainingIgnoreCase(String name);

    Page<Group> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name, Pageable paging);

    List<Group> findAllByMembersContaining(final User members);

    Page<Group> findByMembersContaining(User user, Pageable paging);
}


