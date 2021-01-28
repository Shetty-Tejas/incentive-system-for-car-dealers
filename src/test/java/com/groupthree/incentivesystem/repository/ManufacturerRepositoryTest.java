package com.groupthree.incentivesystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.repositories.ManufacturerRepository;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
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
		Manufacturer manufacturerFromDb = manufacturerRepo.getOne(getManufacturer().getManufacturerId());
		assertThat(manufacturerFromDb).isEqualTo(manufacturerInDb);
	} 
	
}
