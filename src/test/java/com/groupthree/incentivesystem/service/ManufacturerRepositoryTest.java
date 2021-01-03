package com.groupthree.incentivesystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.repositories.ManufacturerRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ManufacturerRepositoryTest {

	@MockBean
	private ManufacturerRepository manufacturerRepo;
	
	public Manufacturer getManufacturer() {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setManufacturerId(1);
		manufacturer.setManufacturerName("Maruti Suzuki");
		manufacturer.setManufacturerPass("1234");
		return manufacturer;
	}
	
	@Test
	public void testExistsByManufacturerName() {
		Manufacturer manufacturer = getManufacturer();
		Manufacturer manufacturerInDb = manufacturerRepo.save(manufacturer);
		Manufacturer manufacturerFromDb = manufacturerRepo.getOne(manufacturerInDb.getManufacturerId());
		assertThat(manufacturerFromDb).isEqualTo(manufacturerInDb);
	} 
	
}
