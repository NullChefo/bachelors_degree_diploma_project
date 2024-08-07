package com.nullchefo.carservcehistorybackend.model;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
