package com.stefan.carservcehistorybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.carservcehistorybackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
