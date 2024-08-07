package com.stefan.carservcehistorybackend.model;

import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
