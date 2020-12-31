package com.groupthree.incentivesystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	List<Customer> findByDealerId(int dId);
	List<Customer> findByCustomerContact(long contact);
}
