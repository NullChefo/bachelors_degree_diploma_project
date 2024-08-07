package com.nullchefo.carservcehistorybackend.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.carservcehistorybackend.model.Car;

import jakarta.transaction.Transactional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	@Transactional
	Set<Car> findAllByUserId(Long userId);
}
