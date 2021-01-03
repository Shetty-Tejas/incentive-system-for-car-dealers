package com.groupthree.incentivesystem.repository;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.repositories.CarRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class CarRepositoryTest {

	@Autowired
	CarRepository carRepo;
	
	public Car getCar() {
		Car car = new Car();
		car.setCarModel("Swift");
		car.setCarManufacturer("Maruti");
		car.setCarBasePrice(500000);
		car.setCarMsp(700000);
		return car;
	}
	
	@Test
	public void testFindByCarManufacturer() {
		List<Car> allCar = carRepo.findByCarManufacturer("Maruti");
		assertThat(allCar);
	}
}
