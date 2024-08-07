package com.stefan.carservcehistorybackend.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(name = "The car name", type = "string")
	private String name;
	private String manufacturer;
	private String model;
	private String year;
	private String plateNumber;
	private Long userId;

	/*
	//TODO fix this implementation
	@OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE)
	private Set<CarEventRelation> carEventRelation;
	 */

//	@ManyToOne
//	private User user;

	@ManyToMany(mappedBy = "carId", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Set<Event> carEvents;

	@ManyToMany(mappedBy = "carId", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Set<EventHistory> eventHistories;

}
