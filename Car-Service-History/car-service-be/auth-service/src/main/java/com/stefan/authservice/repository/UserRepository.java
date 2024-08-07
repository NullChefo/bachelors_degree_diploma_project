package com.stefan.authservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.authservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Transactional
	User findByUsername(String username);
	@Transactional
	User findByEmail(String toLowerCase);
}
