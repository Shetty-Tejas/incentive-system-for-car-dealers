package com.groupthree.incentivesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * The main method to launch the Spring application.
 * 
 * @author Tejas
 *
 */
@SpringBootApplication
@ComponentScan({ "com.groupthree.incentivesystem", "com.traineemanagementsystem.services" })
@EntityScan("com.groupthree.incentivesystem.entities")
@EnableJpaRepositories("com.groupthree.incentivesystem.repositories")
public class IncentiveSystemApplication {
	/**
	 * Main method
	 * @param args Arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(IncentiveSystemApplication.class, args);
	}

}
