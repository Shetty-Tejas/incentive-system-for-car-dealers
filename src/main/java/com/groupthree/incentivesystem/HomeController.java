package com.groupthree.incentivesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a Rest Controller for the home page.
 * @author Tejas
 *
 */
@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger("HomeController.class");
	
	/**
	 * This method is used to go to the home-page.
	 * @return
	 */
	@GetMapping("/")
	public String home() {
		logger.info("The service is online.");
		return "Welcome to Incentive System for Car Dealers!";
	}
}
