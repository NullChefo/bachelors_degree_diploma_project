package com.stefan.carservcehistorybackend.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefan.carservcehistorybackend.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	@Transactional
	Set<Car> findAllByUserId(Long userId);
}
