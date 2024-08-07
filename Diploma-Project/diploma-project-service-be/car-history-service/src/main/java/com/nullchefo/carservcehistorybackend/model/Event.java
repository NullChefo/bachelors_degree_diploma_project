package com.nullchefo.carservcehistorybackend.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long carId;
	private Date date;
	private Long currentMileage;
	private String body;
	private Boolean periodicEvent;
	private Date nextDate;
	private Long changeAfterMileage;

	/*
	@OneToMany(mappedBy = "event")
	private Set<CarEventRelation> carEventRelation;
	 */

}
