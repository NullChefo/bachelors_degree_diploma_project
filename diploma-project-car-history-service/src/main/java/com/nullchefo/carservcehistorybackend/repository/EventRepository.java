package com.nullchefo.carservcehistorybackend.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nullchefo.carservcehistorybackend.model.Event;

import jakarta.transaction.Transactional;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Transactional
	Set<Event> findAllByCarId(Long carId);
}
