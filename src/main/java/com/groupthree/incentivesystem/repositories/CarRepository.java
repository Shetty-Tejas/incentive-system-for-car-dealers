package com.groupthree.incentivesystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, String>{
	
}
