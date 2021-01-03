package com.groupthree.incentivesystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.repositories.CarRepository;
import com.groupthree.incentivesystem.repositories.DealsRepository;
import com.groupthree.incentivesystem.repositories.ManufacturerRepository;
import com.groupthree.incentivesystem.services.ManufacturerService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ManufacturerServiceTest {

	@InjectMocks
	private ManufacturerService manufacturerService;
	
	@MockBean
	private ManufacturerRepository manufacturerRepo;
	private CarRepository carRepo;
	private DealsRepository dealsRepo;
	
	public Manufacturer getManufacturer() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setManufacturerId(1);
		manufacturer.setManufacturerName("Maruti Suzuki");
		manufacturer.setManufacturerPass("1234");
		return manufacturer;
	}
	
	/*@Test
	@Order(1)
	@Rollback(false)
	public void testInsertCar() {
		Car car = new Car();
		car.setCarManufacturer("Maruti Suzuki");
		car.setCarModel("Swift");
		car.setCarBasePrice(500000);
		car.setCarMsp(700000);
		
		Mockito.when(carRepo.saveAndFlush(car)).thenReturn(car);
		assertThat(manufacturerService.insertCar(car)).isEqual(car);
		
	}*/
	
	@Test
	@Order(1)
	@Rollback(false)
	public void testFetchManufacturerName() {
		String manufacturerByName = manufacturerService.fetchManufacturerName(1);
		assertThat(manufacturerByName);
	}
	
	@Test
	@Order(2)
	@Rollback(false)
	public void testFetchAllCars() {
		List<Car> allCars = carRepo.findAll();
		assertThat(allCars);
	}
	
	/*@Test
	@Order(3)
	@Rollback(false)
	public void testFetchAllDeals() {
		List<Deals> allDeals = dealsRepo.findAll();
		assertThat(allDeals);
	}*/	
}
