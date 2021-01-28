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

/**
 * This is a Service class for Validations throughout the application.
 * 
 * @author Tejas/Snehal
 *
 */
@Service
public class ValidatorService {

	private static final Logger VS_LOGGER = LoggerFactory.getLogger(ValidatorService.class);

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
	private final String emailPattern = "^([a-z]+([\\._]\1{2})?)+@[a-z]+[\\.][a-z]{2,5}$"; // Tweak this and change the
																							// javadocs.

	/**
	 * This method validates whether the dealer ID exists in the dealer database.
	 * 
	 * @param dId Only parameter for the method, accepts the dealer ID.
	 * @return True if the dealer ID exists in the database, else False.
	 */
	public boolean dealerIdValidator(int dId) {
		VS_LOGGER.info("Dealer ID Validation in process");
		return dealerRepo.existsById(dId);
	}

	/**
	 * This method validates whether the password entered matches a format.
	 * 
	 * @param pass Only parameter for the method, accepts the password.
	 * @return True if the password matches the format, else False.
	 */
	public boolean passValidator(String pass) {
		VS_LOGGER.info("Password Validation in process");
		return pass.matches(passwordPattern);
	}

	/**
	 * This method validates whether the name entered matches a format.
	 * @param name Only parameter for the method, accepts the name.
	 * @return True if the name matches the format, else False.
	 */
	public boolean nameValidator(String name) {
		VS_LOGGER.info("Name Validation in process");
		return name.matches(namePattern);
	}

	/**
	 * This method validates whether the contact number entered matches a format.
	 * @param contact Only parameter for the method, accepts the contact number.
	 * @return True if the contact matches the format, else False.
	 */
	public boolean contactValidator(long contact) {
		VS_LOGGER.info("Contact Validation in process");
		return String.valueOf(contact).matches(contactPattern);
	}

	/**
	 * This method validates whether a specific contact number exists for a dealer (to avoid redundancy). 
	 * @param contact Only parameter for the method, accepts the contact number.
	 * @return True if the contact number doesn't exist, else False.
	 */
	public boolean checkIfContactExists(long contact) {
		VS_LOGGER.info("Checking if contact exists or not");
		return !dealerRepo.existsByDealerContact(contact);
	}

	/**
	 * This method validates whether a car exists in the car repository.
	 * @param dealModel Only parameter for the method, accepts the model string.
	 * @return True if the car exists, else False.
	 */
	public boolean carExistsValidator(String dealModel) {
		VS_LOGGER.info("Checking whether Car Exists");
		return carRepo.existsById(dealModel);
	}
	
	/**
	 * This method validates whether a car doesn't exists in the car repository.
	 * @param dealModel Only parameter for the method, accepts the model string.
	 * @return True if the car doesn't exists, else False.
	 */
	public boolean carDoesntExistsValidator(String dealModel) {
		VS_LOGGER.info("Checking whether Car doesn't Exists");
		return !carRepo.existsById(dealModel);
	}

	/**
	 * This method validates whether a deal exists in the deal repository.
	 * @param dealModel Only parameter for the method, accepts the model string.
	 * @return True if deal exists, else False.
	 */
	public boolean dealExistsValidator(String dealModel) {
		VS_LOGGER.info("Validating whether deal exists");
		return dealsRepo.existsById(dealModel);
	}
	
	/**
	 * This method validates whether a deal doesn't exist in the deal repository.
	 * @param dealModel Only parameter for the method, accepts the model string.
	 * @return True if deal doesn't exist, else False.
	 */
	public boolean dealDoesntExistsValidator(String dealModel) {
		VS_LOGGER.info("Validating whether deal doesn't exists");
		return !dealsRepo.existsById(dealModel);
	}

	/**
	 * This method validates whether the incentive range follows a specific format.
	 * @param incentiveRange Only parameter for the method, accepts the incentive range string.
	 * @return True if it follows the format, else False.
	 */
	public boolean incentiveRangeValidator(String incentiveRange) {
		VS_LOGGER.info("Validating Incentive Range");
		int index = incentiveRange.indexOf('-');
		try {
			int minRange = Integer.parseInt(incentiveRange.substring(0, index));
			int maxRange = Integer.parseInt(incentiveRange.substring(index + 1));
			return !(minRange >= maxRange || minRange < 0 || maxRange > 100);
		} catch (NumberFormatException e) {
			VS_LOGGER.error("NumberFormatException Occured", e);
			return false;
		}
	}

	/**
	 * This method validates whether the date is in the present or in the past but not in the future.
	 * @param date Only parameter for the method, accepts the date string.
	 * @return True if not in the future, else False.
	 */
	public boolean dealDateValidator(LocalDate date) {
		VS_LOGGER.info("Validates Deal Dates");
		LocalDate now = LocalDate.now();
		Period period = date.until(now);
		return (period.getDays() >= 0);
	}

	/**
	 * This method validates whether a specific date is approved.
	 * @param model Only parameter for the method, accepts the model string.
	 * @return True if approved, else False.
	 */
	public boolean checkIfDealApproved(String model) {
		if (this.dealExistsValidator(model)) {
			VS_LOGGER.info("Deal exists");
			List<Deals> approvedDeals = dealsRepo.findByDealStatus("Approved");
			return approvedDeals.stream().anyMatch(d -> d.getDealModel().equals(model));
		} else {
			VS_LOGGER.info("Deal does not exist.");
			return false;
		}
	}

	/**
	 * This method validates whether the manufacturer ID exists in the manufacturer database.
	 * 
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return True if the manufacturer ID exists in the database, else False.
	 */
	public boolean manufacturerIdValidation(int mId) {
		VS_LOGGER.info("Manufacturer ID Validation in process");
		return manRepo.existsById(mId);
	}

	/**
	 * This method validates whether the manufacturer Email follows a specific format.
	 * @param mEmail Only parameter for the method, accepts the manufacturer Email
	 * @return True if the email follows the format, else False.
	 */
	public boolean manufacturerEmailValidator(String mEmail) {
		VS_LOGGER.info("Manufacturer Email Validation in process");
		return mEmail.matches(emailPattern);
	}

	/**
	 * This method validates whether the manufacturer with a name exists (to avoid redundancy).
	 * @param mName Only parameter for the method, accepts the manufacturer name.
	 * @return True if the name doesn't already exists, else False.
	 */
	public boolean manufacturerExistsValidation(String mName) {
		VS_LOGGER.info("Check if Manufacturer Already exists");
		return !manRepo.existsByManufacturerName(mName);
	}

	/**
	 * This method validates whether the car model follows a specific format.
	 * @param model Only parameter for the method, accepts the model string.
	 * @return True if the model follows the format, else False.
	 */
	public boolean carModelValidation(String model) {
		VS_LOGGER.info("Check if Car Already Exists");
		return model.matches("^\\w+$");
	}

	/**
	 * This method validates whether the prices of the car are proper.
	 * @param carBasePrice First parameter for the method, accepts the car base price.
	 * @param carMsp Second parameter for the method, accepts the car msp.
	 * @return True if validated, else False.
	 */
	public boolean carPriceValidation(long carBasePrice, long carMsp) {
		VS_LOGGER.info("Car Price Validation in process");
		return (carBasePrice < carMsp);
	}
}
