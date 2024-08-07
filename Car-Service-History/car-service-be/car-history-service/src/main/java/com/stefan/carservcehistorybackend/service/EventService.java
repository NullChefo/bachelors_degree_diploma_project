package com.stefan.carservcehistorybackend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefan.carservcehistorybackend.dto.EventDTO;
import com.stefan.carservcehistorybackend.model.Event;
import com.stefan.carservcehistorybackend.repository.EventRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	public ResponseEntity<?> getAll() {

		List<Event> events;

		try {
			events = eventRepository.findAll();
		} catch (Exception e) {

			return ResponseEntity.status(402).body("There are no events"); // TODO change & improve
		}
		if (events.isEmpty()) {

			return ResponseEntity.status(404).body("There are no events yet");
		}

		return ResponseEntity.ok(events);
	}

	public ResponseEntity<?> getEventsForCarId(final Long carId) {

		if (carId == null || carId == 0) {
			return ResponseEntity.status(402).body("Enter valid car id!");
		}

		Set<Event> events;

		try {
			events = eventRepository.findAllByCarId(carId);
		} catch (Exception e) {

			return ResponseEntity.status(402).body("There are no events"); // TODO change & improve
		}
		if (events.isEmpty()) {

			return ResponseEntity.status(404).body("There are no events yet");
		}

		return ResponseEntity.ok(events);

	}

	public ResponseEntity<?> createEvent(final EventDTO eventDTO) {

		Event event = buildEvent(eventDTO);

		event.setId(null);

		try {
			eventRepository.save(event);
		} catch (Exception e) {
			return ResponseEntity.status(403).body("Problem with saving");

		}
		System.out.printf(event.toString());

		return ResponseEntity.ok(event);

	}

	private Event buildEvent(final EventDTO eventDTO) {
		return Event.builder()
					.id(eventDTO.getId())
					.carId(eventDTO.getCarId())
					.date(eventDTO.getDate())
					.currentMileage(eventDTO.getCurrentMileage())
					.body(eventDTO.getBody())
					.periodicEvent(eventDTO.getPeriodicEvent())
					.nextDate(eventDTO.getNextDate())
					.changeAfterMileage(eventDTO.getChangeAfterMileage()).build();
	}

	public ResponseEntity<?> updateEvent(final EventDTO eventDTO) {

		Optional<Event> event;

		try {
			event = eventRepository.findById(eventDTO.getId());

		} catch (Exception e) {

			return ResponseEntity.status(402).body("There is problem with this request: " + e);
		}

		if (event.isEmpty()) {

			return ResponseEntity.status(404).body("No event find to be updated");
		}

		Event eventToSaveToSave = buildEvent(eventDTO);
		eventToSaveToSave.setId(eventDTO.getId());
		try {
			eventRepository.save(eventToSaveToSave);

		} catch (Exception e) {
			return ResponseEntity.status(402).body("The event has not been saved");
		}
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> deleteEvent(final Long eventId) {

		try {
			eventRepository.deleteById(eventId);
		} catch (Exception e) {
			return ResponseEntity.status(402).body("There is problem with this request: " + e);

		}
		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> getEvent(final Long eventId) {

		if (eventId == null || eventId == 0) {
			return ResponseEntity.status(402).body("Enter valid event id!");
		}
		Optional<Event> event;
		try {
			event = eventRepository.findById(eventId);
		} catch (Exception e) {

			return ResponseEntity.status(402).body("There are no events"); // TODO change & improve
		}
		if (event.isEmpty()) {
			return ResponseEntity.status(404).body("There are no events yet");
		}
		return ResponseEntity.ok(event);

	}
}
