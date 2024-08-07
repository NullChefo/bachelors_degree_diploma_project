package com.stefan.authservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.authservice.entity.UserLoginMetrics;

@Repository
public interface UserLoginStatsRepository extends JpaRepository<UserLoginMetrics, Long> {
	@Transactional
	UserLoginMetrics findByUserId(Long id);

	@Transactional
	void deleteAllByUserId(Long userId);
}
