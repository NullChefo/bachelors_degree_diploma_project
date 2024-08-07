package com.nullchefo.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.authservice.entity.UserLoginMetrics;

import jakarta.transaction.Transactional;

@Repository
public interface UserLoginStatsRepository extends JpaRepository<UserLoginMetrics, Long> {
	@Transactional
	UserLoginMetrics findByUserId(Long id);

	@Transactional
	void deleteAllByUserId(Long userId);
}
