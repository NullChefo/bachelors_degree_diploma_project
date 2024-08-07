package com.nullchefo.userservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.userservice.entity.PasswordResetToken;

import jakarta.transaction.Transactional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

	@Transactional
	List<PasswordResetToken> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}

