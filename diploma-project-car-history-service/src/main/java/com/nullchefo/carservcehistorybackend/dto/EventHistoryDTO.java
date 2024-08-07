package com.nullchefo.carservcehistorybackend.dto;

import lombok.Data;

@Data
public class EventHistoryDTO {

	private Long id;
	private Long carId;
	private Long eventId; // optional
	private String event;
}
