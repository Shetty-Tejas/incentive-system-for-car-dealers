package com.groupthree.incentivesystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.repositories.DealsRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class DealsRepositoryTest {

	@Autowired
	DealsRepository dealsRepo;
	
	@Test
	@Order(1)
	@Rollback(false)
	public void testFindByDealStatus() {
		List<Deals> dealsByStatus = dealsRepo.findByDealStatus("Approved");
		assertThat(dealsByStatus);
	}
	
	@Test
	@Order(2)
	@Rollback(false)
	public void testFindByDealManufacturer() {
		List<Deals> dealsByManufacturer = dealsRepo.findByDealManufacturer("Maruti");
		assertThat(dealsByManufacturer);
	}
}
