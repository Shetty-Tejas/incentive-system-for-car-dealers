package com.groupthree.incentivesystem;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.exceptions.FetchEmptyException;
import com.groupthree.incentivesystem.services.CustomerService;

/**
 * This is a Rest Controller for the Customer usecases.
 * @author Snehal
 *
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
	
	/**
	 * Class level logger
	 */
	private static final Logger C_LOGGER = LoggerFactory.getLogger("CustomerController.class"); 
	/**
	 * Customer Service Bean
	 */
	@Autowired
	private CustomerService customerService;
	/**
	 * BodyBuilder object
	 */
	private final BodyBuilder response = ResponseEntity.accepted();
	
	/**
	 * Fetches all approved cars for the customers to see.
	 * @return Returns a Map of the approved Car's name and price.
	 */
	
	@GetMapping("/customer/fetchAllCars")
	public ResponseEntity<?> fetchAllCars(){
		C_LOGGER.info("Displaying all Approved Deals");
		final Map<String, Long> approvedDeals = customerService.fetchApprovedDeals();
		if(approvedDeals.isEmpty()) {
			throw new FetchEmptyException("ERROR : As of now all deals are closed. Sorry :(");
		}
		else {
			return response.body(approvedDeals);
		}
	}
}
