package com.groupthree.incentivesystem;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.services.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customer/fetchAllCars")
	public Map<String, Long> fetchAllCars(){
		return customerService.fetchApprovedDeals();
	}
}
