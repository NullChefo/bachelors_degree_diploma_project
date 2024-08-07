package com.nullchefo.authservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nullchefo.authservice.entity.User;
import com.nullchefo.authservice.entity.UserLoginMetrics;
import com.nullchefo.authservice.repository.UserLoginStatsRepository;
import com.nullchefo.authservice.utils.ClientInfo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthStatsService {

	@Autowired
	private UserLoginStatsRepository userLoginStatsRepository;

	public void saveStats(final User user, ClientInfo clientInfo) {
		UserLoginMetrics userLoginMetrics = userLoginStatsRepository.findByUserId(user.getId());

		if (userLoginMetrics == null) {
			userLoginMetrics = new UserLoginMetrics();
			userLoginMetrics.setCreatedAt(new Date());
		}
		userLoginMetrics.setLoginCount(userLoginMetrics.getLoginCount() + 1);
		userLoginMetrics.setUsername(user.getUsername());
		userLoginMetrics.setUserId(user.getId());

		// TODO make 1 to many userLoginMetrics - clientInfo
		userLoginMetrics.setBrowser(clientInfo.getClientBrowser());
		userLoginMetrics.setIp(clientInfo.getClientIpAddr());
		userLoginMetrics.setLocation("Not available"); // TODO add helper util
		userLoginMetrics.setLastModified(new Date());

		userLoginStatsRepository.save(userLoginMetrics);

	}

	public ResponseEntity<?> deleteMetricsForUser(final Long userId) {

		try{
			userLoginStatsRepository.deleteAllByUserId(userId);
		}
		catch (Exception e){

			return ResponseEntity.status(409).body("There is problem deleting auth metrics. Please try again later!");
		}
		return ResponseEntity.ok().build();
	}
}
