package com.groupthree.incentivesystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.repositories.DealerRepository;
import com.groupthree.incentivesystem.services.DealerService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class DealerServiceTest {

	@InjectMocks
	private DealerService dealerService;
	
	@MockBean
	private DealerRepository dealerRepo;
	
	@Test
	public void testRecordIncentives() {
		Dealer dealer = new Dealer();
		dealer.setDealerIncentive(5000);
	}
	
}
