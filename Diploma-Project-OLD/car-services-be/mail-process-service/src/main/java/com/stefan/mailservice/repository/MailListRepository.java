package com.stefan.mailservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.mailservice.entity.MailList;

@Repository
public interface MailListRepository extends JpaRepository<MailList, Long> {
	@Transactional
		// All not standard methods need to be @Transaction
	boolean deleteByUserId(Long userId);

	MailList findByUserId(Long userId);
}
