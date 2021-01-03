package com.groupthree.incentivesystem;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Incentive;
import com.groupthree.incentivesystem.exceptions.FetchEmptyException;
import com.groupthree.incentivesystem.exceptions.LoginException;
import com.groupthree.incentivesystem.exceptions.ValidationException;
import com.groupthree.incentivesystem.services.DealerService;
import com.groupthree.incentivesystem.services.ValidatorService;

/**
 * This is a Rest Controller for the Dealer usecases.
 * 
 * @author Tejas
 *
 */
@RestController
public class DealerController {

	private static final Logger logger = LoggerFactory.getLogger("DealerController.class");

	@Autowired
	DealerService dealerService;
	@Autowired
	ValidatorService validatorService;

	/**
	 * This method is used to log in the dealer.
	 * 
	 * @param dId   First parameter for the method, accepts the Dealer ID.
	 * @param dPass Second parameter for the method, accepts the Dealer Password.
	 * @return Status whether the login is successful or throws an exception.
	 */
	@PostMapping("/dealer/login")
	public ResponseEntity<?> dealerLogin(@RequestParam int dId, @RequestParam String dPass) {
		logger.info("Dealer Login requested by Dealer ID: " + dId);
		if (validatorService.dealerIdValidator(dId)) {
			if (validatorService.passValidator(dPass)) {
				if (dealerService.dealerLogin(dId, dPass)) {
					logger.info("SUCCESS : Dealer Login Successful!");
					return ResponseEntity.accepted().body("SUCCESS : Dealer Login Successful!");
				} else {
					logger.error("ERROR : Wrong credentials entered for ID: " + dId);
					throw new LoginException("ERROR : Wrong credentials entered for ID: " + dId);
				}
			} else {
				logger.error("ERROR : Password includes illegal characters.");
				throw new ValidationException("ERROR : Password includes illegal characters.");
			}
		} else {
			logger.error("ERROR : ID doesn't exist.");
			throw new ValidationException("ERROR : ID doesn't exist.");
		}
	}

	/**
	 * This method is used to register the dealer.
	 * 
	 * @param dName    First parameter for the method, accepts the Dealer Name.
	 * @param dContact Second parameter for the method, accepts the Dealer contact
	 *                 number (10 digits at max and should start with 7, 8 or 9).
	 * @param dPass    Third parameter for the method, accepts the Dealer Password
	 *                 (Alpha-numeric password with special characters like '$_.@'
	 *                 allowed.
	 * @return Registered Dealer object else throws an exception.
	 */
	@PostMapping("/dealer/register")
	public ResponseEntity<?> dealerRegistration(@RequestParam String dName, @RequestParam long dContact,
			@RequestParam String dPass) {
		logger.info("Dealer Registration requested by Dealer Name: " + dName);
		if (validatorService.nameValidator(dName)) {
			if (validatorService.contactValidator(dContact)) {
				if (validatorService.passValidator(dPass)) {
					if (validatorService.checkIfContactExists(dContact)) {
						logger.info("SUCCESS : Dealer Registration Successful!");
						return ResponseEntity.accepted().body(dealerService.dealerRegistration(dName, dContact, dPass));
					} else {
						logger.error("ERROR : Contact number already exists.");
						throw new ValidationException("ERROR : Contact number already exists.");
					}
				} else {
					logger.error("ERROR : Password includes illegal characters.");
					throw new ValidationException("ERROR : Password includes illegal characters.");
				}
			} else {
				logger.error("ERROR : Contact number is invalid.");
				throw new ValidationException("ERROR : Contact number is invalid.");
			}
		} else {
			logger.error("ERROR : Name is invalid");
			throw new ValidationException("ERROR : Name is invalid");
		}
	}

	/**
	 * This method is used by a logged in dealer to create deals.
	 * 
	 * @param dId            First parameter for the method, accepts the dealer ID
	 *                       by whom the deal is created. Used for logging purposes.
	 * @param dealModel      Second parameter for the method, accepts the car model
	 *                       for which the deal is created. Should be available in
	 *                       the car repository.
	 * @param incentiveRange Third parameter for the method, accepts the incentive
	 *                       percent range, which is in the format "{min}-{max}"
	 *                       where min and max are percent between 1 - 100.
	 * @return Registered Deals object else throws an exception.
	 */
	@PostMapping("/dealer/logged/createDeals")
	public ResponseEntity<?> createDeals(@RequestParam int dId, @RequestParam String dealModel,
			@RequestParam String incentiveRange) {
		logger.info("Deal creation requested by Dealer ID: " + dId);
		if (validatorService.carExistsValidator(dealModel)) {
			if (validatorService.incentiveRangeValidator(incentiveRange)) {
				if (!validatorService.dealExistsValidator(dealModel)) {
					if (validatorService.dealerIdValidator(dId)) {
						logger.info("SUCCESS : Creating deal");
						return ResponseEntity.accepted().body(dealerService.createDeals(dealModel, incentiveRange));
					} else {
						logger.error("ERROR : Dealer ID doesn't exist.");
						throw new ValidationException("ERROR : Dealer ID doesn't exist.");
					}
				} else {
					logger.error("ERROR : Deal already exists.");
					throw new ValidationException("ERROR : Deal already exists.");
				}
			} else {
				logger.error("ERROR : Incentive range is in an invalid format.");
				throw new ValidationException("ERROR : Incentive range is in an invalid format.");
			}

		} else {
			logger.error("ERROR : Car doesn't exists.");
			throw new ValidationException("ERROR : Car doesn't exists.");
		}
	}

	/**
	 * This method is used by a logged in dealer to redefine the previous deals if
	 * those are rejected by the manufacturer.
	 * 
	 * @param dId            First parameter for the method, accepts the dealer ID
	 *                       by whom the deal is redefined. Used for logging
	 *                       purposes.
	 * @param dealModel      Second parameter for the method, accepts the car model
	 *                       for which the deal is created. Should be available in
	 *                       the deal repository.
	 * @param incentiveRange Third parameter for the method, accepts the incentive
	 *                       percent range, which is in the format "{min}-{max}"
	 *                       where min and max are percent between 1 - 100.
	 * @return Redefined Deals object else throws an exception.
	 */
	@PostMapping("/dealer/logged/redefineDeals")
	public ResponseEntity<?> redefineDeals(@RequestParam int dId, @RequestParam String dealModel,
			@RequestParam String incentiveRange) {
		logger.info("Deal redefinition requested by Dealer ID: " + dId);
		if (validatorService.carExistsValidator(dealModel)) {
			if (validatorService.incentiveRangeValidator(incentiveRange)) {
				if (validatorService.dealExistsValidator(dealModel)) {
					if (validatorService.dealerIdValidator(dId)) {
						logger.info("SUCCESS : Redefining deal");
						return ResponseEntity.accepted().body(dealerService.redefineDeals(dealModel, incentiveRange));
					} else {
						logger.error("ERROR : Dealer ID doesn't exist.");
						throw new ValidationException("ERROR : Dealer ID doesn't exist.");
					}
				} else {
					logger.error("ERROR : Deal doesn't exist.");
					throw new ValidationException("ERROR : Deal doesn't exist.");
				}
			} else {
				logger.error("ERROR : Incentive range is in an invalid format.");
				throw new ValidationException("ERROR : Incentive range is in an invalid format.");
			}

		} else {
			logger.error("ERROR : Car doesn't exists.");
			throw new ValidationException("ERROR : Car doesn't exists.");
		}
	}

	/**
	 * This method is used by a logged in dealer to delete unwanted incentive deals.
	 * 
	 * @param dId       First parameter for the method, accepts the dealer ID by
	 *                  whom the deal is deleted. Used for logging purposes.
	 * @param dealModel Second parameter for the method, used to delete the deal for
	 *                  inserted model. Should be available in the deal repository
	 * @return Information about deleted deal else throws an exception.
	 */
	@PostMapping("/dealer/logged/deleteDeals")
	public ResponseEntity<?> deleteDeals(@RequestParam int dId, @RequestParam String dealModel) {
		logger.info("Deal deletion requested by Dealer ID: " + dId);
		if (validatorService.dealExistsValidator(dealModel)) {
			if (validatorService.dealerIdValidator(dId)) {
				logger.info("SUCCESS : Deleting deal");
				return ResponseEntity.accepted().body(dealerService.deleteDeals(dealModel));
			} else {
				logger.error("ERROR : Dealer ID doesn't exist.");
				throw new ValidationException("ERROR : Dealer ID doesn't exist.");
			}
		} else {
			logger.error("ERROR : Deal doesn't exist.");
			throw new ValidationException("ERROR : Deal doesn't exist.");
		}
	}

	/**
	 * This method is used by a logged in dealer to fetch all deals from the deal
	 * repository.
	 * 
	 * @param dId First parameter for the method, accepts the dealer ID by whom all
	 *            the deals are fetched. Used for logging purposes.
	 * @return List of all deals else throws an exception.
	 */
	@GetMapping("/dealer/logged/fetchAllDeals")
	public ResponseEntity<?> fetchAllDeals(@RequestParam int dId) {
		logger.info("Deal fetch requested by Dealer ID: " + dId);
		if (validatorService.dealerIdValidator(dId)) {
			List<Deals> allDeals = dealerService.fetchAllDeals();
			if (allDeals.isEmpty()) {
				logger.error("ERROR : No deals are present.");
				throw new FetchEmptyException("ERROR : No deals are present.");
			} else {
				logger.info("SUCCESS : Fetching all deals.");
				return ResponseEntity.accepted().body(allDeals);
			}
		} else {
			logger.error("ERROR : Dealer ID doesn't exist");
			throw new ValidationException("ERROR : Dealer ID doesn't exist");
		}
	}

	/**
	 * This method is used by a logged in dealer to record incentive details of the
	 * sold cars. This method also records Customer details and adds the incentive
	 * earned to the dealer's account.
	 * 
	 * @param dId       First parameter for the method, accepts the dealer ID by
	 *                  whom the car is sold.
	 * @param contactNo Second parameter for the method, accepts the customer
	 *                  contact number by whom the car is brought (10 digits at max
	 *                  and should start with 7, 8 or 9).
	 * @param custName  Third parameter for the method, accepts the customer name by
	 *                  whom the car is brought.
	 * @param date      Fourth parameter for the method, accepts the date at which
	 *                  the car is brought. The date can't be in the future.
	 * @param model     Fifth parameter for the method, accepts the car model which
	 *                  is brought by the customer. The status of the car in deal
	 *                  repository should be approved.
	 * @return Incentive, customer and dealer information, else throws an exception.
	 */
	@PostMapping("/dealer/logged/recordIncentive")
	public ResponseEntity<?> recordIncentive(@RequestParam int dId, @RequestParam long contactNo,
			@RequestParam String custName, @RequestParam String date, @RequestParam String model) {
		logger.info("Incentive recording requested by Dealer ID: " + dId);
		LocalDate bookDate = dealerService.dateConverter(date);
		if (!bookDate.equals(null) && validatorService.dealDateValidator(bookDate)) {
			if (validatorService.dealerIdValidator(dId)) {
				if (validatorService.nameValidator(custName)) {
					if (validatorService.contactValidator(contactNo)) {
						if (validatorService.checkIfDealApproved(model)) {
							logger.info("SUCCESS : Recording incentive.");
							return ResponseEntity.accepted()
									.body(dealerService.recordIncentives(dId, contactNo, custName, bookDate, model));
						} else {
							logger.error("ERROR : The deal isn't approved yet.");
							throw new ValidationException("ERROR : The deal isn't approved yet.");
						}

					} else {
						logger.error("ERROR : Contact number is invalid.");
						throw new ValidationException("ERROR : Contact number is invalid.");
					}
				} else {
					logger.error("ERROR : Name is invalid");
					throw new ValidationException("ERROR : Name is invalid");
				}
			} else {
				logger.info("ERROR : Dealer ID doesn't exists");
				throw new ValidationException("ERROR : Dealer ID doesn't exists");
			}
		} else {
			logger.error("ERROR : Date is not valid.");
			throw new ValidationException("ERROR : Date is not valid.");
		}

	}

	/**
	 * This method is used by a logged in dealer to fetch all his/her customer
	 * details.
	 * 
	 * @param dId Only parameter for the method, accepts the dealer ID to fetch the
	 *            cars sold by the dealer.
	 * @return List of all customers of dealer, else throws an exception.
	 */
	@GetMapping("/dealer/logged/fetchCustomerById")
	public ResponseEntity<?> fetchCustomerById(@RequestParam int dId) {
		logger.info("Fetching Customer Details by Dealer ID: " + dId);
		if (validatorService.dealerIdValidator(dId)) {
			List<Customer> customerById = dealerService.fetchCustomerRecordsById(dId);
			if (customerById.isEmpty()) {
				logger.error("ERROR : No customers are present.");
				throw new FetchEmptyException("ERROR : No customers are present.");
			} else {
				logger.info("SUCCESS : Fetching customers.");
				return ResponseEntity.accepted().body(customerById);
			}
		} else {
			logger.error("ERROR : Dealer ID doesn't exist.");
			throw new ValidationException("ERROR : Dealer ID doesn't exist.");
		}
	}

	/**
	 * This method is used by a logged in dealer to fetch a customer details by
	 * his/her contact number.
	 * 
	 * @param dId     First parameter for the method, accepts the dealer ID. Used
	 *                for logging purposes.
	 * @param contact Second parameter for the method, accepts the customer contact
	 *                to fetch the customer's details.
	 * @return List of all customers by contact number else throws an exception.
	 */
	@GetMapping("/dealer/logged/fetchCustomerByContact")
	public ResponseEntity<?> fetchCustomerByContact(@RequestParam int dId, @RequestParam long contact) {
		logger.info("Fetching Customer Records by Dealer ID: " + dId);
		if (validatorService.dealerIdValidator(dId)) {
			if (validatorService.contactValidator(contact)) {
				List<Customer> customerByContact = dealerService.fetchCustomerRecordsByContact(contact);
				if (customerByContact.isEmpty()) {
					logger.error("ERROR : No customers with this contact number are present.");
					throw new FetchEmptyException("ERROR : No customers with this contact number are present.");
				} else {
					logger.info("SUCCESS : Fetching customers.");
					return ResponseEntity.accepted().body(customerByContact);
				}
			} else {
				logger.error("ERROR : Contact number is invalid.");
				throw new ValidationException("ERROR : Contact number is invalid.");
			}
		} else {
			logger.error("ERROR : Dealer ID doesn't exist.");
			throw new ValidationException("ERROR : Dealer ID doesn't exist.");
		}
	}

	/**
	 * This method is used by a logged in dealer to fetch all his/her incentive
	 * records.
	 * 
	 * @param dId Only parameter for the method, accepts the dealer ID to fetch the
	 *            incentive records.
	 * @return List of all incentive records of a particular dealer, else throws an
	 *         exception.
	 */
	@GetMapping("/dealer/logged/fetchIncentiveRecords")
	public ResponseEntity<?> fetchIncentiveRecordsind(@RequestParam int dId) {
		logger.info("Fetching Incentive Records by Dealer ID: " + dId);
		if (validatorService.dealerIdValidator(dId)) {
			List<Incentive> incentiveRecords = dealerService.fetchIncentiveRecordsById(dId);
			if (incentiveRecords.isEmpty()){
				logger.error("ERROR : No incentive records found for this dealer ID.");
				throw new FetchEmptyException("ERROR : No incentive records found for this dealer ID.");
			}
			else {
				logger.info("SUCCESS : Fetching incentive records.");
				return ResponseEntity.accepted().body(incentiveRecords);
			}
		} else {
			logger.error("ERROR : Dealer ID doesn't exist.");
			throw new ValidationException("ERROR : Dealer ID doesn't exist.");
		}
	}
}
