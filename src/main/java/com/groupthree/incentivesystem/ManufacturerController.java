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

/**
 * This is a Rest Controller for the Manufacturer usecases.
 * @author Tejas
 *
 */
@RestController
public class ManufacturerController {

	private static final Logger logger = LoggerFactory.getLogger("ManufacturerController.class");
	
	@Autowired
	ManufacturerService manufacturerService;
	@Autowired
	ValidatorService validatorService;

	/**
	 * This method is used to log in the manufacturer.
	 * @param mId First parameter for the method, accepts the Manufacturer ID.
	 * @param mPass Second parameter for the method, accepts the Manufacturer Password.
	 * @return
	 */
	@PostMapping("/manufacturer/login")
	public String manufacturerLogin(@RequestParam int mId, @RequestParam String mPass) {
		logger.info("Manufacturer Login requested by the Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId) && 
				validatorService.passValidator(mPass)) {
			if (manufacturerService.manufacturerLogin(mId, mPass))
				return "Logged in!";
			else
				return "Wrong credentials.";
		} else
			return "Validation failed!";
	}

	/**
	 * This method is used to register the manufacturer.
	 * @param mName First parameter for the method, accepts the Manufacturer (Company) Name.
	 * @param mEmail Second parameter for the method, accepts the Manufacturer (Company) Email.
	 * @param mPass Third parameter for the method, accepts the Dealer Password (Alpha-numeric password with special characters like '$_.@' allowed.
	 * @return
	 */
	@PostMapping("/manufacturer/register")
	public Manufacturer manufacturerRegister(@RequestParam String mName, @RequestParam String mEmail,
			@RequestParam String mPass) {
		logger.info("Manufacturer Registration registered by Manufacturer Name: " + mName);
		if (validatorService.nameValidator(mName) && 
				validatorService.manufacturerEmailValidator(mEmail) && 
				validatorService.passValidator(mPass) && 
				validatorService.manufacturerExistsValidation(mName)) {
			return manufacturerService.manufacturerRegister(mName, mEmail, mPass);
		} else
			return new Manufacturer(null, null, null);
	}

	/**
	 * This method is used by a logged in manufacturer to insert new car in the car repository.
	 * @param mId First parameter for the method, accepts the manufacturer ID.
	 * @param carModel Second parameter for the method, accepts the car model. Can be alphanumeric and shouldn't be existing in car repository already.
	 * @param carBasePrice Third parameter for the method, accepts the base price for the corresponding car model.
	 * @param carMsp Fourth parameter for the method, accepts the maximum selling price for the corresponding car model
	 * @return
	 */
	@PostMapping("/manufacturer/logged/insertCar")
	public Car insertCar(@RequestParam int mId, @RequestParam String carModel, @RequestParam long carBasePrice,
			@RequestParam long carMsp) {
		logger.info("Car insertion requested by Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId) && 
				!validatorService.carExistsValidator(carModel) && 
				validatorService.carModelValidation(carModel) && 
				validatorService.carPriceValidation(carBasePrice, carMsp)) {
			return manufacturerService.insertCar(mId, carModel, carBasePrice, carMsp);
		} else
			return new Car(null, null, 0l, 0l);
	}

	/**
	 * This method is used by a logged in  manufacturer to alter the status of the deals created by the dealer.
	 * @param mId First parameter for the method, accepts the manufacturer ID. Used for logging purposes.
	 * @param carModel Second parameter for the method, accepts the carModel. Should be present in the deals repository.
	 * @param flag Third parameter for the method, accepts either "true" or "false".
	 * @return
	 */
	@PostMapping("/manufacturer/logged/alterStatus")
	public Deals alterStatus(@RequestParam int mId, @RequestParam String carModel, @RequestParam boolean flag) {
		logger.info("Status alteration requested by the Manufacturer ID: " + mId);
		if (validatorService.dealExistsValidator(carModel)) {
			return manufacturerService.updateDealStatus(carModel, flag);
		} else
			return new Deals(null, null, 0l, 0l, null);
	}

	/**
	 * This method is used by a logged in manufacturer to fetch all the deals from the deals repository for the corresponding manufacturer.
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return
	 */
	@GetMapping("/manufacturer/logged/fetchAllDeals")
	public List<Deals> fetchAllDeals(@RequestParam int mId) {
		logger.info("Deal fetch requested by Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			return manufacturerService.fetchAllDeals(mId);
		} else
			return new LinkedList<>();
	}

	/**
	 * This method is used by a logged in manufacturer to fetch all the cars from the cars repository for the corresponding manufacturer.
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return
	 */
	@GetMapping("/manufacturer/logged/fetchAllCars")
	public List<Car> fetchAllCars(@RequestParam int mId) {
		logger.info("Car fetch requested by Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			return manufacturerService.fetchAllCars(mId);
		} else
			return new LinkedList<>();
	}

}
