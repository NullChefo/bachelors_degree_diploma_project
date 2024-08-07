package com.nullchefo.socialmediaservice.repository;

import com.nullchefo.socialmediaservice.entity.Connection;
import com.nullchefo.socialmediaservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    List<Connection> findAllByUserAndAcceptedAndDeleted(User currentUser, boolean accepted, boolean deleted);

    Connection findByUserAndConnectionAndDeleted(User currentUser, User user, boolean deleted);

    Page<Connection> findAllByUserAndAcceptedAndDeletedOrderByConnectionRequestDateDesc(
            User currentUser,
            boolean accepted,
            boolean deleted,
            Pageable paging);

    Page<Connection> findAllByUserAndAcceptedAndDeletedOrderByConnectionDateDesc(
            User currentUser,
            boolean accepted,
            boolean deleted,
            Pageable pageable);

    List<Connection> findAllByUserAndDeleted(User user, boolean deleted);
    //	@Transactional
    //	List<Friend> findByUserIdOrFriendId(Long userId, Long userId1);
}





