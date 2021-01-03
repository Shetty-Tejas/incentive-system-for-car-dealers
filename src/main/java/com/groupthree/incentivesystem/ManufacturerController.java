package com.groupthree.incentivesystem;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.exceptions.FetchEmptyException;
import com.groupthree.incentivesystem.exceptions.LoginException;
import com.groupthree.incentivesystem.exceptions.ValidationException;
import com.groupthree.incentivesystem.services.ManufacturerService;
import com.groupthree.incentivesystem.services.ValidatorService;

/**
 * This is a Rest Controller for the Manufacturer usecases.
 * 
 * @author Snehal
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
	 * 
	 * @param mId   First parameter for the method, accepts the Manufacturer ID.
	 * @param mPass Second parameter for the method, accepts the Manufacturer
	 *              Password.
	 * @return Status whether the login is successful or throws an exception.
	 */
	@PostMapping("/manufacturer/login")
	public ResponseEntity<?> manufacturerLogin(@RequestParam int mId, @RequestParam String mPass) {
		logger.info("Manufacturer Login requested by the Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			if (validatorService.passValidator(mPass)) {
				if (manufacturerService.manufacturerLogin(mId, mPass)) {
					logger.error("SUCCESS : Logged in!");
					return ResponseEntity.accepted().body("SUCCESS : Logged in!");
				} else {
					logger.error("ERROR : Wrong credentials for the ID: " + mId);
					throw new LoginException("ERROR : Wrong credentials for the ID: " + mId);
				}
			} else {
				logger.error("ERROR : The password contains illegal characters.");
				throw new ValidationException("ERROR : The password contains illegal characters.");
			}
		} else {
			logger.error("ERROR : Manufacturer ID doesn't exist.");
			throw new ValidationException("ERROR : Manufacturer ID doesn't exist.");
		}
	}

	/**
	 * This method is used to register the manufacturer.
	 * 
	 * @param mName  First parameter for the method, accepts the Manufacturer
	 *               (Company) Name.
	 * @param mEmail Second parameter for the method, accepts the Manufacturer
	 *               (Company) Email.
	 * @param mPass  Third parameter for the method, accepts the Dealer Password
	 *               (Alpha-numeric password with special characters like '$_.@'
	 *               allowed.
	 * @return Registered Manufacturer object else throws an exception.
	 */
	@PostMapping("/manufacturer/register")
	public ResponseEntity<?> manufacturerRegister(@RequestParam String mName, @RequestParam String mEmail,
			@RequestParam String mPass) {
		logger.info("Manufacturer Registration registered by Manufacturer Name: " + mName);
		if (validatorService.nameValidator(mName)) {
			if (validatorService.manufacturerEmailValidator(mEmail)) {
				if (validatorService.passValidator(mPass)) {
					if (validatorService.manufacturerExistsValidation(mName)) {
						logger.info("SUCCESS : Registering Manufacturer.");
						return ResponseEntity.accepted()
								.body(manufacturerService.manufacturerRegister(mName, mEmail, mPass));
					} else {
						logger.error("ERROR : Manufacturer for this name already exists.");
						throw new ValidationException("ERROR : Manufacturer for this name already exists.");
					}
				} else {
					logger.error("ERROR : Password is in an invalid format.");
					throw new ValidationException("ERROR : Password is in an invalid format.");
				}
			} else {
				logger.error("ERROR : Email is in an invalid format.");
				throw new ValidationException("ERROR : Email is in an invalid format.");
			}
		} else {
			logger.error("ERROR : Name is in an invalid format.");
			throw new ValidationException("ERROR : Name is in an invalid format.");
		}
	}

	/**
	 * This method is used by a logged in manufacturer to insert new car in the car
	 * repository.
	 * 
	 * @param mId          First parameter for the method, accepts the manufacturer
	 *                     ID.
	 * @param carModel     Second parameter for the method, accepts the car model.
	 *                     Can be alphanumeric and shouldn't be existing in car
	 *                     repository already.
	 * @param carBasePrice Third parameter for the method, accepts the base price
	 *                     for the corresponding car model.
	 * @param carMsp       Fourth parameter for the method, accepts the maximum
	 *                     selling price for the corresponding car model
	 * @return Registered Car object else throws an exception.
	 */
	@PostMapping("/manufacturer/logged/insertCar")
	public ResponseEntity<?> insertCar(@RequestParam int mId, @RequestParam String carModel,
			@RequestParam long carBasePrice, @RequestParam long carMsp) {
		logger.info("Car insertion requested by Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			if (validatorService.carModelValidation(carModel)) {
				if (!validatorService.carExistsValidator(carModel)) {
					if (validatorService.carPriceValidation(carBasePrice, carMsp)) {
						return ResponseEntity.accepted()
								.body(manufacturerService.insertCar(mId, carModel, carBasePrice, carMsp));
					} else {
						logger.error("ERROR : Car price invalid.");
						throw new ValidationException("ERROR : Car price invalid.");
					}
				} else {
					logger.error("ERROR : Car already exists.");
					throw new ValidationException("ERROR : Car already exists.");
				}
			} else {
				logger.error("ERROR : Car model is in an invalid format.");
				throw new ValidationException("ERROR : Car model is in an invalid format.");
			}
		} else {
			logger.error("ERROR : Manufacturer ID doesn't exist.");
			throw new ValidationException("ERROR : Manufacturer ID doesn't exist.");
		}
	}

	/**
	 * This method is used by a logged in manufacturer to alter the status of the
	 * deals created by the dealer.
	 * 
	 * @param mId      First parameter for the method, accepts the manufacturer ID.
	 *                 Used for logging purposes.
	 * @param carModel Second parameter for the method, accepts the carModel. Should
	 *                 be present in the deals repository.
	 * @param flag     Third parameter for the method, accepts either "true" or
	 *                 "false".
	 * @return Altered Deals object else throws an exception.
	 */
	@PostMapping("/manufacturer/logged/alterStatus")
	public ResponseEntity<?> alterStatus(@RequestParam int mId, @RequestParam String carModel,
			@RequestParam boolean flag) {
		logger.info("Status alteration requested by the Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			if (validatorService.dealExistsValidator(carModel)) {
				logger.info("SUCCESS : Altering status.");
				return ResponseEntity.accepted().body(manufacturerService.updateDealStatus(carModel, flag));
			} else {
				logger.error("ERROR : Deal doesn't exists.");
				throw new ValidationException("ERROR : Deal doesn't exists.");
			}
		} else {
			logger.error("ERROR : Manufacturer ID doesn't exist.");
			throw new ValidationException("ERROR : Manufacturer ID doesn't exist.");
		}
	}

	/**
	 * This method is used by a logged in manufacturer to fetch all the deals from
	 * the deals repository for the corresponding manufacturer.
	 * 
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return List of all deals for a manufacturer, else throws an exception.
	 */
	@GetMapping("/manufacturer/logged/fetchAllDeals")
	public ResponseEntity<?> fetchAllDeals(@RequestParam int mId) {
		logger.info("Deal fetch requested by Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			List<Deals> allDeals = manufacturerService.fetchAllDeals(mId);
			if (allDeals.isEmpty()) {
				logger.error("ERROR : No deals exist.");
				throw new FetchEmptyException("ERROR : No deals exist.");
			} else {
				logger.info("SUCCESS : Fetching all deals.");
				return ResponseEntity.accepted().body(allDeals);
			}
		} else {
			logger.error("ERROR : Manufacturer ID doesn't exist.");
			throw new ValidationException("ERROR : Manufacturer ID doesn't exist.");
		}
	}

	/**
	 * This method is used by a logged in manufacturer to fetch all the cars from
	 * the cars repository for the corresponding manufacturer.
	 * 
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return List of all cars for the manufacturer, else throws an exception.
	 */
	@GetMapping("/manufacturer/logged/fetchAllCars")
	public ResponseEntity<?> fetchAllCars(@RequestParam int mId) {
		logger.info("Car fetch requested by Manufacturer ID: " + mId);
		if (validatorService.manufacturerIdValidation(mId)) {
			List<Car> allCars = manufacturerService.fetchAllCars(mId);
			if (allCars.isEmpty()) {
				logger.error("ERROR : No cars found.");
				throw new FetchEmptyException("ERROR : No cars found.");
			}
			else {
				logger.info("SUCCESS : Fetching all cars.");
				return ResponseEntity.accepted().body(allCars);
			}
		} else {
			logger.error("ERROR : Manufacturer ID doesn't exist.");
			throw new ValidationException("ERROR : Manufacturer ID doesn't exist.");
		}
	}

}
