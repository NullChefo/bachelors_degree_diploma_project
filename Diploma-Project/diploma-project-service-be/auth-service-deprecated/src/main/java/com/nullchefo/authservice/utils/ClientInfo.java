package com.nullchefo.authservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class ClientInfo {

	private final String referer;
	private final String fullURL;
	private final String clientOS;
	private final String clientIpAddr;
	private final String clientBrowser;

	public ClientInfo(HttpServletRequest request) {
		this.referer = getReferer(request);
		this.fullURL = getFullURL(request);
		this.clientIpAddr = getClientIpAddr(request);
		this.clientOS = getClientOS(request);
		this.clientBrowser = getClientBrowser(request);
		//	this.userAgent  = getUserAgent(request);
		//	this.ip = request.getRemoteAddr();
	}

	private String getReferer(HttpServletRequest request) {
		final String referer = request.getHeader("referer");
		return referer;
	}

	private String getFullURL(HttpServletRequest request) {
		final StringBuffer requestURL = request.getRequestURL();
		final String queryString = request.getQueryString();

		final String result = queryString == null ? requestURL.toString() : requestURL.append('?')
																					  .append(queryString)
																					  .toString();

		return result;
	}

	//http://stackoverflow.com/a/18030465/1845894
	private String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	//http://stackoverflow.com/a/18030465/1845894
	private String getClientOS(HttpServletRequest request) {
		final String browserDetails = request.getHeader("User-Agent");

		//=================OS=======================
		final String lowerCaseBrowser = browserDetails.toLowerCase();
		if (lowerCaseBrowser.contains("windows")) {
			return "Windows";
		} else if (lowerCaseBrowser.contains("mac")) {
			return "Mac";
		} else if (lowerCaseBrowser.contains("x11")) {
			return "Unix";
		} else if (lowerCaseBrowser.contains("android")) {
			return "Android";
		} else if (lowerCaseBrowser.contains("iphone")) {
			return "IPhone";
		} else {
			return "Unknown: " + browserDetails;
		}
	}

	private String getClientBrowser(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

}
