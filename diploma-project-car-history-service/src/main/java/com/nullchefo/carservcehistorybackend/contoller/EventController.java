package com.nullchefo.carservcehistorybackend.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nullchefo.carservcehistorybackend.dto.EventDTO;
import com.nullchefo.carservcehistorybackend.service.EventService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/event/v1/")
@SecurityRequirement(name = "Bearer Authentication")
public class EventController {

	private final EventService eventService;

	public EventController(final EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<?> getEvent(@PathVariable final Long eventId) {
		return eventService.getEvent(eventId);
	}

	@GetMapping("/")
	public ResponseEntity<?> getEvents() { // TODO add pagination
		return eventService.getAll();
	}

	@GetMapping("/car/{carId}")
	public ResponseEntity<?> getEventsForCar(@PathVariable final Long carId) { // TODO add pagination
		return eventService.getEventsForCarId(carId);
	}

	@PostMapping("/")
	public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
		return eventService.createEvent(eventDTO);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO) { // TODO add pagination
		return eventService.updateEvent(eventDTO);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteEvent(@RequestParam Long eventId) {
		return eventService.deleteEvent(eventId);
	}

}
