package com.groupthree.incentivesystem.services;

import org.springframework.stereotype.Service;

@Service
public class DealerService {
	private final String passwordPattern = "^[a-zA-Z0-9|_|$|\\.|@]+$";
	final String namePattern = "^[a-zA-Z ]{3,34}$";
	DealerDAO dealerObj = new DealerDAOImpl();
	DealsDAO dealsObj = new DealsDAOImpl();
	ManufacturerDAO manuObj = new ManufacturerDAOImpl();
	CarDAO carObj = new CarDAOImpl();
}
