package com.groupthree.incentivesystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.repositories.DealerRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class DealerRepositoryTest {
	
	@Autowired
	private DealerRepository dealerRepo;
	
	public Dealer getDealer() {
		Dealer dealer = new Dealer();
		dealer.setDealerId(1);
		dealer.setDealerName("Snehal");
		dealer.setDealerContact(900478930);
		dealer.setDealerPass("123456");
		dealer.setDealerIncentive(5000);
		return dealer;
	}
	
	@Test
	@Order(1)
	@Rollback(false)
	public void testExistsByDealerName() {
		Dealer dealer = getDealer();
		Dealer savedInDb = dealerRepo.save(dealer);
		Dealer getFromDb = dealerRepo.getOne(savedInDb.getDealerId());
		assertThat(getFromDb).isEqualTo(savedInDb);
		
	}
	
	@Test
	@Order(2)
	public void testExistsByDealerContact() {
		Dealer dealer = getDealer();
		Dealer savedInDb = dealerRepo.save(dealer);
		Dealer getFromDb = dealerRepo.getOne(savedInDb.getDealerId());
		assertThat(getFromDb).isEqualTo(savedInDb);
	}
	
}