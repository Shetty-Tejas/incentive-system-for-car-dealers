package com.groupthree.incentivesystem.repository;

import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.repositories.CustomerRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Test
	@Order(1)
	@Rollback(false)
	public void testFetchCustomerRecordsById() {
		List<Customer> customerById = customerRepo.findByDealerId(1);
		assertThat(customerById);
	}
	
	@Test
	@Order(2)
	public void testFetchCustomerRecordsByContact() {
		List<Customer> customerByContact = customerRepo.findByCustomerContact(900478930);
		assertThat(customerByContact);
	}
}
