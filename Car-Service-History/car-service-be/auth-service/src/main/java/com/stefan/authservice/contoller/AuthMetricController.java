package com.stefan.authservice.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefan.authservice.service.UserAuthStatsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth-metrics")
@AllArgsConstructor
public class AuthMetricController {

	@Autowired
	private UserAuthStatsService userAuthStatsService;


	@DeleteMapping("/{userId}")
	public ResponseEntity<?> logout(@PathVariable final Long userId) {
		return userAuthStatsService.deleteMetricsForUser(userId);
	}


}
