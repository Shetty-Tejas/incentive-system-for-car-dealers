package com.groupthree.incentivesystem.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.repositories.CustomerRepository;
import com.groupthree.incentivesystem.repositories.DealsRepository;
import com.groupthree.incentivesystem.services.CustomerService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;
	
	@MockBean
	private CustomerRepository customerRepo;
	@MockBean
	private DealsRepository dealsRepo;
	
	public Customer getCustomerId() {
		Customer customer = new Customer();
		customer.setCustomerContact(900478930);
		customer.setCustomerName("Raj");
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate date = LocalDate.parse("yyyy-mm-dd" , format);
		customer.setCustomerPurchaseDate(Date.valueOf(date));
		customer.setDealerId(1);
		customer.setPurchasedCar("Swift");
		customer.setPurchasedCarManf("Maruti Suzuki");
		customer.setTotalCost(700000);
		return customer;		
	}
	
	@Test
	public void testFetchApprovedDeals() {
		List<Deals> approvedDeals = dealsRepo.findByDealStatus("Approved");
		Map<String, Long> deals = approvedDeals.stream().collect(Collectors.toMap(Deals::nameToString, Deals::getCarMsp));
		assertThat(deals);
	}
}
