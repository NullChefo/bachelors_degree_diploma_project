package com.stefan.carservcehistorybackend.contoller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.stefan.carservcehistorybackend.dto.CarDTO;
import com.stefan.carservcehistorybackend.service.CarService;

@RestController
@RequestMapping("/car/v1/")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping("/")
	public ResponseEntity<?> getCars() { // TODO add pagination
		return carService.getAll();
	}

	@GetMapping("/{carId}")
	public ResponseEntity<?> getCar(@PathVariable final Long carId) { // TODO add pagination
		return carService.getCar(carId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getCarsForUser(@PathVariable final Long userId) { // TODO add pagination
		return carService.getCarsForUserId(userId);
	}

	@PostMapping("/")
	public ResponseEntity<?> createCar(@RequestBody CarDTO carDTO) {
		return carService.createCar(carDTO);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateCar(@RequestBody CarDTO carDto) { // TODO add pagination
		return carService.updateCar(carDto);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCar(@RequestParam Long carId) {
		return carService.delete(carId);
	}

}
