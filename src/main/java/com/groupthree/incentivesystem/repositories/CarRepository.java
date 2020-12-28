package com.groupthree.incentivesystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, String>{
	public List<Car> findByCarManufacturer(String manufacturer);
}
