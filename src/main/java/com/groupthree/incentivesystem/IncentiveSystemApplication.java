package com.groupthree.incentivesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.groupthree.incentivesystem", "com.traineemanagementsystem.services"})
@EntityScan("com.groupthree.incentivesystem.entities")
@EnableJpaRepositories("com.groupthree.incentivesystem.repositories")
public class IncentiveSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncentiveSystemApplication.class, args);
	}

}
