package com.groupthree.incentivesystem;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.services.ManufacturerService;
import com.groupthree.incentivesystem.services.ValidatorService;

@RestController
public class ManufacturerController {

	private static final Logger logger = LoggerFactory.getLogger("ManufacturerController.class");
	
	@Autowired
	ManufacturerService manufacturerService;
	@Autowired
	ValidatorService validatorService;

	@PostMapping("/manufacturer/login")
	public String manufacturerLogin(@RequestParam int mId, @RequestParam String mPass) {
		logger.info("Manufacturer Login");
		if (validatorService.manufacturerIdValidation(mId) && 
				validatorService.passValidator(mPass)) {
			if (manufacturerService.manufacturerLogin(mId, mPass))
				return "Logged in!";
			else
				return "Wrong credentials.";
		} else
			return "Validation failed!";
	}

	@PostMapping("/manufacturer/register")
	public Manufacturer manufacturerRegister(@RequestParam String mName, @RequestParam String mEmail,
			@RequestParam String mPass) {
		logger.info("Manufacturer Registration");
		if (validatorService.nameValidator(mName) && 
				validatorService.manufacturerEmailValidator(mEmail) && 
				validatorService.passValidator(mPass) && 
				validatorService.manufacturerExistsValidation(mName)) {
			return manufacturerService.manufacturerRegister(mName, mEmail, mPass);
		} else
			return new Manufacturer(null, null, null);
	}

	@PostMapping("/manufacturer/logged/insertCar")
	public Car insertCar(@RequestParam int mId, @RequestParam String carModel, @RequestParam long carBasePrice,
			@RequestParam long carMsp) {
		logger.info("Inserting Car Details");
		if (validatorService.manufacturerIdValidation(mId) && 
				!validatorService.carExistsValidator(carModel) && 
				validatorService.carModelValidation(carModel) && 
				validatorService.carPriceValidation(carBasePrice, carMsp)) {
			return manufacturerService.insertCar(mId, carModel, carBasePrice, carMsp);
		} else
			return new Car(null, null, 0l, 0l);
	}

	@PostMapping("/manufacturer/logged/alterStatus")
	public Deals alterStatus(@RequestParam String carModel, @RequestParam boolean flag) {
		if (validatorService.dealExistsValidator(carModel)) {
			logger.info("Altering Status");
			return manufacturerService.updateDealStatus(carModel, flag);
		} else
			return new Deals(null, null, 0l, 0l, null);
	}

	@GetMapping("/manufacturer/logged/fetchAllDeals")
	public List<Deals> fetchAllDeals(@RequestParam int mId) {
		if (validatorService.manufacturerIdValidation(mId)) {
			logger.info("Fetching All Deals");
			return manufacturerService.fetchAllDeals(mId);
		} else
			return new LinkedList<>();
	}

	@GetMapping("/manufacturer/logged/fetchAllCars")
	public List<Car> fetchAllCars(@RequestParam int mId) {
		if (validatorService.manufacturerIdValidation(mId)) {
			logger.info("Fetching all Cars Details");
			return manufacturerService.fetchAllCars(mId);
		} else
			return new LinkedList<>();
	}

}
