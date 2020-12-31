package com.groupthree.incentivesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger("HomeController.class");
	
	@GetMapping("/")
	public String home() {
		logger.info("Home Controller Started");
		return "Welcome to Incentive System for Car Dealers!";
	}
}
