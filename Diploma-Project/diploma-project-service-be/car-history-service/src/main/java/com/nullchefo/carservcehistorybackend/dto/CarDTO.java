package com.nullchefo.carservcehistorybackend.dto;

import lombok.Data;

@Data
public class CarDTO {

	private Long id;
	private String name;
	private String manufacturer;
	private String model;
	private String year;
	private String plateNumber;
	private Long userId;
}
