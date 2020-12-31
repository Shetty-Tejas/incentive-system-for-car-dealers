package com.groupthree.incentivesystem;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.services.CustomerService;

@RestController
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger("CustomerController.class"); 
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customer/fetchAllCars")
	public Map<String, Long> fetchAllCars(){
		logger.info("Displaying all Approved Deals");
		return customerService.fetchApprovedDeals();
	}
}
