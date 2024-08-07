package com.stefan.carservcehistorybackend.dto;

import lombok.Data;

@Data
public class CarEventRelationDTO {

	private Long id;
	private CarDTO car;
	private EventDTO event;

}
