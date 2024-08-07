package com.stefan.carservcehistorybackend.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stefan.carservcehistorybackend.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Transactional
	Set<Event> findAllByCarId(Long carId);
}
