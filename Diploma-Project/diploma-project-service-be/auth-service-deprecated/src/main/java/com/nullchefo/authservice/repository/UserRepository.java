package com.nullchefo.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.authservice.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Transactional
	User findByUsername(String username);

	@Transactional
	User findByEmail(String toLowerCase);
}
