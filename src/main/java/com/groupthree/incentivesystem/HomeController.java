package com.groupthree.incentivesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a Rest Controller for the home page.
 * @author Tejas
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
	
	/**
	 * Class level logger
	 */
	private static final Logger H_LOGGER = LoggerFactory.getLogger("HomeController.class");
	
	/**
	 * This method is used to go to the home-page.
	 * @return
	 */
	@GetMapping("/")
	public String home() {
		H_LOGGER.info("The service is online.");
		return "Welcome to Incentive System for Car Dealers!";
	}
}
