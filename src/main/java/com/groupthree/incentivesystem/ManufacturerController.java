 package com.groupthree.incentivesystem;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.exceptions.ErrorMessagesConstants;
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
@CrossOrigin(origins = "http://localhost:3000")
public class ManufacturerController {
	/**
	 * Class level logger
	 */
	private static final Logger M_LOGGER = LoggerFactory.getLogger("ManufacturerController.class");

	/**
	 * Manufacturer Service class declaration
	 */
	@Autowired
	private ManufacturerService manufService;
	/**
	 * Validator Service class declaration
	 */
	@Autowired
	private ValidatorService validatorService;
	/**
	 * BodyBuilder object
	 */
	private final BodyBuilder response = ResponseEntity.accepted();

	/**
	 * This method is used to log in the manufacturer.
	 * 
	 * @param mId   First parameter for the method, accepts the Manufacturer ID.
	 * @param mPass Second parameter for the method, accepts the Manufacturer
	 *              Password.
	 * @return Status whether the login is successful or throws an exception.
	 */
	@PostMapping("/manufacturer/login")
	public ResponseEntity<?> manufacturerLogin(@RequestParam final int mId, @RequestParam final String mPass) {
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Manufacturer Login requested by the Manufacturer ID: " + mId);
		}
		if (validatorService.manufacturerIdValidation(mId)) {
			if (validatorService.passValidator(mPass)) {
				if (manufService.manufacturerLogin(mId, mPass)) {
					M_LOGGER.info("SUCCESS : Logged in!");
					return response.body(mId);
				} else {
					throw new LoginException(ErrorMessagesConstants.WRONG_CRED + mId);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.PASS_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> manufacturerRegister(@RequestParam final String mName, @RequestParam final String mEmail,
			@RequestParam final String mPass) {
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Manufacturer Registration registered by Manufacturer Name: " + mName);
		}
		if (validatorService.nameValidator(mName)) {
			if (validatorService.manufacturerEmailValidator(mEmail)) {
				if (validatorService.passValidator(mPass)) {
					if (validatorService.manufacturerExistsValidation(mName)) {
						M_LOGGER.info("SUCCESS : Registering Manufacturer.");
						return response.body(manufService.manufacturerRegister(mName, mEmail, mPass));
					} else {
						throw new ValidationException(ErrorMessagesConstants.NAME_ERR);
					}
				} else {
					throw new ValidationException(ErrorMessagesConstants.PASS_ERR);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.EMAIL_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.NAME_FORMAT);
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
	public ResponseEntity<?> insertCar(@RequestParam final int mId, @RequestParam final String carModel,
			@RequestParam final long carBasePrice, @RequestParam final long carMsp) {
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Car insertion requested by Manufacturer ID: " + mId);
		}
		if (validatorService.manufacturerIdValidation(mId)) {
			if (validatorService.carModelValidation(carModel)) {
				if (validatorService.carDoesntExistsValidator(carModel)) {
					if (validatorService.carPriceValidation(carBasePrice, carMsp)) {
						M_LOGGER.info("SUCCESS : Inserting car.");
						return response.body(manufService.insertCar(mId, carModel, carBasePrice, carMsp));
					} else {
						throw new ValidationException(ErrorMessagesConstants.CAR_PRICE);
					}
				} else {
					throw new ValidationException(ErrorMessagesConstants.CAR_EXISTS);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.CAR_FORMAT);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> alterStatus(@RequestParam final int mId, @RequestParam final String carModel,
			@RequestParam final boolean flag) {
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Status alteration requested by the Manufacturer ID: " + mId);
		}
		if (validatorService.manufacturerIdValidation(mId)) {
			if (validatorService.dealExistsValidator(carModel)) {
				M_LOGGER.info("SUCCESS : Altering status.");
				return response.body(manufService.updateDealStatus(carModel, flag));
			} else {
				throw new ValidationException(ErrorMessagesConstants.DEAL_NOT_EXIST);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> fetchAllDeals(@RequestParam final int mId) {
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Deal fetch requested by Manufacturer ID: " + mId);
		}
		if (validatorService.manufacturerIdValidation(mId)) {
			final List<Deals> allDeals = manufService.fetchAllDeals(mId);
			if (allDeals.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.DEAL_NOT_EXIST);
			} else {
				M_LOGGER.info("SUCCESS : Fetching all deals.");
				return response.body(allDeals);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> fetchAllCars(@RequestParam final int mId) {
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Car fetch requested by Manufacturer ID: " + mId);
		}
		if (validatorService.manufacturerIdValidation(mId)) {
			final List<Car> allCars = manufService.fetchAllCars(mId);
			if (allCars.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.CAR_NOT_EXIST);
			} else {
				M_LOGGER.info("SUCCESS : Fetching all cars.");
				return response.body(allCars);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
	
	@GetMapping("/manufacturer/logged/getProfile")
	public ResponseEntity<?> getProfile(@RequestParam final int mId){
		if (M_LOGGER.isInfoEnabled()) {
			M_LOGGER.info("Fetching profile for Manufacturer ID: " + mId);
		}
		if (validatorService.manufacturerIdValidation(mId)) {
			final Manufacturer manufacturer = manufService.getProfile(mId);
			if (manufacturer.equals(null)) {
				throw new FetchEmptyException(ErrorMessagesConstants.ID_ERR);
			} else {
				M_LOGGER.info("SUCCESS : Fetching all cars.");
				return response.body(manufacturer);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
}
