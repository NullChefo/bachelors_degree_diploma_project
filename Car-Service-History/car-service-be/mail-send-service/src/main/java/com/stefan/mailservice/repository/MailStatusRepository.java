package com.stefan.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.mailservice.entity.MailStatus;

@Repository
public interface MailStatusRepository extends JpaRepository<MailStatus, Long> {
	void deleteByUserId(Long userId);
}
