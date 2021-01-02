package com.groupthree.incentivesystem.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.repositories.CarRepository;
import com.groupthree.incentivesystem.repositories.DealerRepository;
import com.groupthree.incentivesystem.repositories.DealsRepository;
import com.groupthree.incentivesystem.repositories.ManufacturerRepository;

@Service
public class ValidatorService {

	private final Logger logger = LoggerFactory.getLogger(ValidatorService.class);
	
	@Autowired
	ManufacturerRepository manRepo;
	@Autowired
	DealerRepository dealerRepo;
	@Autowired
	CarRepository carRepo;
	@Autowired
	DealsRepository dealsRepo;

	private final String passwordPattern = "^[a-zA-Z0-9|_|$|\\.|@]+$";
	private final String namePattern = "^[a-zA-Z ]{3,34}$"; // Tweak this and change the javadocs.
	private final String contactPattern = "^[987][0-9]{9}$";
	private final String emailPattern = "^([a-z]+([\\._]\1{2})?)+@[a-z]+[\\.][a-z]{2,5}$"; // Tweak this and change the javadocs.

	/* Dealer Validations */

	// Validates Dealer Id
	public boolean dealerIdValidator(int dId) {
		logger.info("Dealer ID Validation in process");
		return dealerRepo.existsById(dId);
	}

	// Validates Dealer / Manufacturer Password
	public boolean passValidator(String pass) {
		logger.info("Password Validation in process");
		return pass.matches(passwordPattern);
	}

	// Validated Dealer / Customer / Manufacturer Name
	public boolean nameValidator(String name) {
		logger.info("Name Validation in process");
		return name.matches(namePattern);
	}

	// Validated Dealer / Customer Contact
	public boolean contactValidator(long contact) {
		logger.info("Contact Validation in process");
		return String.valueOf(contact).matches(contactPattern);
	}
	
	// Check if contact number exists for dealer
	public boolean checkIfContactExists(long contact) {
		logger.info("Checking if contact exists or not");
		return !dealerRepo.existsByDealerContact(contact);
	}

	// Validates whether the car exists
	public boolean carExistsValidator(String dealModel) {
		logger.info("Checking whether Car Exists");
		return carRepo.existsById(dealModel);
	}

	// Validates whether the deal exists
	public boolean dealExistsValidator(String dealModel) {
		logger.info("Validating whether deal exists");
		return dealsRepo.existsById(dealModel);
	}

	// Validates the incentive range
	public boolean incentiveRangeValidator(String incentiveRange) {
		logger.info("Validating Incentive Range");
		int index = incentiveRange.indexOf('-');
		try {
			int minRange = Integer.parseInt(incentiveRange.substring(0, index));
			int maxRange = Integer.parseInt(incentiveRange.substring(index + 1));
			return !(minRange >= maxRange || minRange < 0 || maxRange > 100);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException Occured", e);;
			return false;
		}
	}

	// Validates whether the date is in present or in the past.
	public boolean dealDateValidator(LocalDate date) {
		logger.info("Validates Deal Dates");
		LocalDate now = LocalDate.now();
		Period period = date.until(now);
		return (period.getDays() >= 0);
	}

	// Check if deal is approved
	public boolean checkIfDealApproved(String model) {
		if (this.dealExistsValidator(model)) {
			logger.info("Deal Approved");
			List<Deals> approvedDeals = dealsRepo.findByDealStatus("Approved");
			return approvedDeals.stream().anyMatch(d -> d.getDealModel().equals(model));
		} else {
			logger.info("Deal not Approved");
			return false;
		}
	}

	/* Manufacturer Validations */
	
	// Manufacturer Id validation
	public boolean manufacturerIdValidation(int mId) {
		logger.info("Manufacturer ID Validation in process");
		return manRepo.existsById(mId);
	}

	// Manufacturer Email validation
	public boolean manufacturerEmailValidator(String mEmail) {
		logger.info("Manufacturer Email Validation in process");
		return mEmail.matches(emailPattern);
	}
	
	// Check if manufacturer already exists
	public boolean manufacturerExistsValidation(String mName) {
		logger.info("Check if Manufacturer Already exists");
		return !manRepo.existsByManufacturerName(mName);
	}
	
	// Car modelname validator
	public boolean carModelValidation(String model) {
		logger.info("Check if Car Already Exists");
		return model.matches("^\\w+$");
	}
	
	// Car price validation
	public boolean carPriceValidation(long carBasePrice, long carMsp) {
		logger.info("Car Price Validation in process");
		return (carBasePrice < carMsp);
	}
}
