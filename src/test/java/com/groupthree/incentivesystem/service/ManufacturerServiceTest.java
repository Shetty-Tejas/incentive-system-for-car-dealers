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

	@MockBean
	private ManufacturerService manufacturerService;
	@MockBean
	private ManufacturerRepository manufacturerRepo;
	@MockBean
	private CarRepository carRepo;
	@MockBean
	private DealsRepository dealsRepo;
	
	public Manufacturer getManufacturer() {
		Manufacturer manufacturer = new Manufacturer("Yamaha", "yamaha@yamaha.com", "yamaha");
		return manufacturer;
	}
	
	@Test
	@Order(1)
	@Rollback(false)
	public void testFetchManufacturerName() {
		Manufacturer manufacturer = new Manufacturer("Yamaha", "yamaha@yamaha.com", "yamaha");
		Mockito.when(manufacturerRepo.save(manufacturer)).thenReturn(getManufacturer());
		assertThat(manufacturerRepo.findById(1));
	}
	
	@Test
	@Order(2)
	@Rollback(false)
	public void testInsertCar() {
		Manufacturer m = getManufacturer();
		Car car = new Car("Yamaha", "FZ25", 125000, 150000);
		manufacturerRepo.saveAndFlush(m);
		assertThat(manufacturerService.insertCar(m.getManufacturerId(), "FZ25", 125000, 150000));
	}
	
	@Test
	@Order(3)
	@Rollback(false)
	public void testFetchAllCars() {
		List<Car> allCars = carRepo.findAll();
		assertThat(allCars);
	}
	
	@Test
	@Order(4)
	@Rollback(false)
	public void testFetchAllDeals() { 
		getManufacturer();
		List<Deals> allDeals = dealsRepo.findByDealManufacturer("Bugatti");
		assertThat(allDeals);
	}	
}
