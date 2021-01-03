package com.groupthree.incentivesystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.entities.Incentive;
import com.groupthree.incentivesystem.repositories.CustomerRepository;
import com.groupthree.incentivesystem.repositories.DealerRepository;
import com.groupthree.incentivesystem.repositories.IncentiveRepository;
import com.groupthree.incentivesystem.services.DealerService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

public class DealerServiceTest {

	@InjectMocks
	private DealerService dealerService;
	
	@MockBean
	private DealerRepository dealerRepo;
	@MockBean
	private CustomerRepository customerRepo;
	@MockBean
	private IncentiveRepository incentiveRepo;
	
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
	public void testFetchIncentiveRecordsById() {
		List<Incentive> incentiveById = incentiveRepo.findByDealerId(1);
		assertThat(incentiveById);
	}
	
	@Test
	@Order(2)
	@Rollback(false)
	public void testFetchCustomerRecordsById() {
		List<Customer> customerById = customerRepo.findByDealerId(1);
		assertThat(customerById);
	}
	
	@Test
	@Order(3)
	public void testFetchCustomerRecordsByContact() {
		List<Customer> customerByContact = customerRepo.findByCustomerContact(900478930);
		assertThat(customerByContact);
	}
	
	/*@Test
	public void testRecordIncentives() {
		Dealer dealer = new Dealer();
		dealer.setDealerIncentive(5000);
	}*/
	
}