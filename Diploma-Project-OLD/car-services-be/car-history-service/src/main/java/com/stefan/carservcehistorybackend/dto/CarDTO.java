package com.stefan.carservcehistorybackend.dto;

import java.util.Set;

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
