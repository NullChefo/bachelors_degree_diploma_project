package com.nullchefo.mailservice.DTO;

import lombok.Data;

@Data
public class MailListInfo {
	private boolean signedForAnnouncements;
	private boolean signedForPromotions;
	private boolean signedForNotifications;
}
