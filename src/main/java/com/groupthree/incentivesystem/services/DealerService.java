package com.groupthree.incentivesystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.repositories.CarRepository;
import com.groupthree.incentivesystem.repositories.DealerRepository;
import com.groupthree.incentivesystem.repositories.DealsRepository;
import com.groupthree.incentivesystem.repositories.ManufacturerRepository;

@Service
public class DealerService {
	private final String passwordPattern = "^[a-zA-Z0-9|_|$|\\.|@]+$";
	private final String namePattern = "^[a-zA-Z ]{3,34}$";
	private final String contactPattern = "^[987][0-9]{9}$";

	Dealer dObj;

	@Autowired
	DealerRepository dealerRepo;
	@Autowired
	DealsRepository dealsRepo;
	@Autowired
	ManufacturerRepository manufacturerRepo;
	@Autowired
	CarRepository carRepo;

	// Login Validator
	public boolean validator(int dId, String dPass) {
		if (dPass.matches(passwordPattern) && dealerRepo.existsById(dId))
			return true;
		else
			return false;
	}
	
	// Registration Validator
	public boolean validator(String dName, long dContact, String dPass) {
		if (dPass.matches(passwordPattern) && 
				dName.matches(namePattern) && 
				String.valueOf(dContact).matches(contactPattern) && 
				!dealerRepo.existsByDealerName(dName))
			return true;
		else
			return false;
	}
	
	// Dealer Login
	public boolean dealerLogin(int dId, String dPass) {
		dObj = dealerRepo.findById(dId).get();
		if (dObj.getDealerPass().equals(dPass))
			return true;
		else
			return false;
	}
	
	//Dealer Registration
	public Dealer dealerRegistration(String dName, long dContact, String dPass) {
		dObj = new Dealer(dName, dContact, dPass);
		return dealerRepo.save(dObj);
	}
	
	// Create Deals
	
}
