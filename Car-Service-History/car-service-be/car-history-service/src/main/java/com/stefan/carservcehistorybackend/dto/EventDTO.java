package com.stefan.carservcehistorybackend.dto;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EventDTO {

	private Long id;
	private Long carId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	private Long currentMileage;
	private String body;
	private Boolean periodicEvent;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date nextDate;
	private Long changeAfterMileage;

}
