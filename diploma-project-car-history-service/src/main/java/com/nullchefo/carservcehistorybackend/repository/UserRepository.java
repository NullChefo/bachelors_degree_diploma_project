package com.nullchefo.carservcehistorybackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.carservcehistorybackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
