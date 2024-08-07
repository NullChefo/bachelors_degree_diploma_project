package com.nullchefo.userservice.cron;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nullchefo.userservice.entity.PasswordResetToken;
import com.nullchefo.userservice.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetCronJob {

	private final PasswordResetTokenRepository passwordResetTokenRepository;

	public PasswordResetCronJob(final PasswordResetTokenRepository passwordResetTokenRepository) {
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}

	// At 03:00 every day
	@Scheduled(cron = "00 03 * * * ?", zone = "Europe/Frankfurt")
	public void deleteAllExpiredTokens() {
		LocalDateTime from = LocalDateTime.now().minusDays(9999);
		LocalDateTime to = LocalDateTime.now().minusDays(1);
		List<PasswordResetToken> passwordResetCronJobList = passwordResetTokenRepository.findByCreatedAtBetween(
				from,
				to);
		passwordResetTokenRepository.deleteAll(passwordResetCronJobList);

	}

}
