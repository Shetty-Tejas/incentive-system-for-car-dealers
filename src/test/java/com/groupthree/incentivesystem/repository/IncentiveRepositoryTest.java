package com.groupthree.incentivesystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Incentive;
import com.groupthree.incentivesystem.repositories.IncentiveRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class IncentiveRepositoryTest {

	@Autowired
	IncentiveRepository incentiveRepo;
	
	@Test
	public void testFindByDealerId() {
		List<Incentive> incentiveByDealerId = incentiveRepo.findByDealerId(1);
		assertThat(incentiveByDealerId);
	}
}
