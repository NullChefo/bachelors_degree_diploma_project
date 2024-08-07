package com.nullchefo.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.mailservice.entity.MailList;

import jakarta.transaction.Transactional;

@Repository
public interface MailListRepository extends JpaRepository<MailList, Long> {
	@Transactional
	boolean deleteByUserId(Long userId);

	MailList findByUserId(Long userId);
}
