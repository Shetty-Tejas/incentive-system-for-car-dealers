package com.groupthree.incentivesystem.test.repository;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.repositories.DealerRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class DealerRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DealerRepository dealerRepo;
	
	@Test
	@Order(1)
	public void testExistsByDealerName() {
		Dealer dealer = new Dealer();
		dealer.setDealerName("Snehal");
	}
	
	@Test
	@Order(2)
	public Dealer testExistsByDealerContact() {
		Dealer dealer = new Dealer();
		dealer.setDealerContact(900478552);
		return dealer;
	}
	
}
