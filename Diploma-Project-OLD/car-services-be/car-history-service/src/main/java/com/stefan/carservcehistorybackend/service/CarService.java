package com.stefan.carservcehistorybackend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stefan.carservcehistorybackend.dto.CarDTO;
import com.stefan.carservcehistorybackend.model.Car;
import com.stefan.carservcehistorybackend.repository.CarRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarService {

	@Autowired
	private CarRepository carRepository;

	private static Car buildCar(final CarDTO carDTO) {
		return Car.builder()
				  .id(carDTO.getId()) // TODO if needed remove
				  .name(carDTO.getName())
				  .manufacturer(carDTO.getManufacturer())
				  .model(carDTO.getModel())
				  .year(carDTO.getYear())
				  .plateNumber(carDTO.getPlateNumber())
				  .userId(carDTO.getUserId())
				  .build();
	}

	public ResponseEntity<?> createCar(CarDTO carDTO) {

		Car car = buildCar(carDTO);

		car.setId(null);

		try {
			carRepository.save(car);
		} catch (Exception e) {
			return ResponseEntity.status(403).body("Problem with saving");

		}

		return ResponseEntity.ok(car);

	}

	public ResponseEntity<?> delete(Long carId) {

		try {
			carRepository.deleteById(carId);
		} catch (Exception e) {
			return ResponseEntity.status(402).body("There is problem with this request: " + e);

		}
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<?> getAll() {

		List<Car> cars;

		try {
			cars = carRepository.findAll();
		} catch (Exception e) {

			return ResponseEntity.status(402).body("There are no cars"); // TODO change & improve
		}
		if (cars.isEmpty()) {

			return ResponseEntity.status(404).body("There are no cars yet");
		}

		return ResponseEntity.ok(cars);
	}

	public ResponseEntity<?> updateCar(CarDTO carDto) {

		Optional<Car> car;

		try {
			car = carRepository.findById(carDto.getId());

		} catch (Exception e) {

			return ResponseEntity.status(402).body("There is problem with this request: " + e);
		}

		if (car.isEmpty()) {

			return ResponseEntity.status(404).body("No car find to be updated");
		}

		Car carToSave = buildCar(carDto);
		carToSave.setId(carDto.getId());

		System.out.println(carToSave.getId());

		try {
			carRepository.save(carToSave);

		} catch (Exception e) {

			return ResponseEntity.status(402).body("The car has not been saved");

		}
		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> getCarsForUserId(final Long userId) {

		if (userId == null || userId == 0) {
			return ResponseEntity.status(402).body("Enter valid user id!");
		}

		Set<Car> cars;

		try {
			cars = carRepository.findAllByUserId(userId);
		} catch (Exception e) {

			return ResponseEntity.status(402).body("There are no cars"); // TODO change & improve
		}
		if (cars.isEmpty()) {

			return ResponseEntity.status(404).body("There are no cars yet");
		}

		return ResponseEntity.ok(cars);

	}

	public ResponseEntity<?> getCar(final Long carId) {


		if (carId == null || carId == 0) {
			return ResponseEntity.status(402).body("Enter valid car id!");
		}

		Optional<Car> car;

		try {
			car = carRepository.findById(carId);
		} catch (Exception e) {

			return ResponseEntity.status(402).body("There are no car"); // TODO change & improve
		}
		if (car.isEmpty()) {

			return ResponseEntity.status(404).body("There are no car yet");
		}

		return ResponseEntity.ok(car);


	}
}
