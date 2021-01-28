package com.groupthree.incentivesystem;

import java.time.LocalDate;
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
import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Incentive;
import com.groupthree.incentivesystem.exceptions.ErrorMessagesConstants;
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
@CrossOrigin(origins = "http://localhost:3000")
public class DealerController {

	/**
	 * Class level logger
	 */
	private static final Logger D_LOGGER = LoggerFactory.getLogger("DealerController.class");
	/**
	 * BodyBuilder object
	 */
	private final BodyBuilder response = ResponseEntity.accepted();

	/**
	 * Dealer Service class declaration
	 */
	@Autowired
	private DealerService dealerService;
	/**
	 * Validator Service class declaration
	 */
	@Autowired
	private ValidatorService validatorService;

	/**
	 * This method is used to log in the dealer.
	 * 
	 * @param dId   First parameter for the method, accepts the Dealer ID.
	 * @param dPass Second parameter for the method, accepts the Dealer Password.
	 * @return ID if the login is successful or else returns wrong credentials string.
	 */
	@PostMapping("/dealer/login")
	public ResponseEntity<?> dealerLogin(@RequestParam final int dId, @RequestParam final String dPass) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Dealer Login requested by Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			if (validatorService.passValidator(dPass)) {
				if (dealerService.dealerLogin(dId, dPass)) {
					D_LOGGER.info("SUCCESS : Dealer Login Successful!");
					return response.body(dId);
				} else {
					throw new LoginException(ErrorMessagesConstants.WRONG_CRED + dId);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.PASS_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> dealerRegistration(@RequestParam final String dName, @RequestParam final long dContact,
			@RequestParam final String dPass) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Dealer Registration requested by Dealer Name: " + dName);
		}
		if (validatorService.nameValidator(dName)) {
			if (validatorService.contactValidator(dContact)) {
				if (validatorService.passValidator(dPass)) {
					if (validatorService.checkIfContactExists(dContact)) {
						D_LOGGER.info("SUCCESS : Dealer Registration Successful!");
						return response.body(dealerService.dealerRegistration(dName, dContact, dPass));
					} else {
						throw new ValidationException(ErrorMessagesConstants.CONTACT_EXIST);
					}
				} else {
					throw new ValidationException(ErrorMessagesConstants.PASS_ERR);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.CONTACT_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.NAME_FORMAT);
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
	public ResponseEntity<?> createDeals(@RequestParam final int dId, @RequestParam final String dealModel,
			@RequestParam final String incentiveRange) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Deal creation requested by Dealer ID: " + dId);
		}
		if (validatorService.carExistsValidator(dealModel)) {
			if (validatorService.incentiveRangeValidator(incentiveRange)) {
				if (validatorService.dealDoesntExistsValidator(dealModel)) {
					if (validatorService.dealerIdValidator(dId)) {
						D_LOGGER.info("SUCCESS : Creating deal");
						return response.body(dealerService.createDeals(dealModel, incentiveRange));
					} else {
						throw new ValidationException(ErrorMessagesConstants.ID_ERR);
					}
				} else {
					throw new ValidationException(ErrorMessagesConstants.DEAL_EXIST);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.INCENTIVE_FORMAT);
			}

		} else {
			throw new ValidationException(ErrorMessagesConstants.CAR_NOT_EXIST);
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
	public ResponseEntity<?> redefineDeals(@RequestParam final int dId, @RequestParam final String dealModel,
			@RequestParam final String incentiveRange) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Deal redefinition requested by Dealer ID: " + dId);
		}
		if (validatorService.carExistsValidator(dealModel)) {
			if (validatorService.incentiveRangeValidator(incentiveRange)) {
				if (validatorService.dealExistsValidator(dealModel)) {
					if (validatorService.dealerIdValidator(dId)) {
						D_LOGGER.info("SUCCESS : Redefining deal");
						return response.body(dealerService.redefineDeals(dealModel, incentiveRange));
					} else {
						throw new ValidationException(ErrorMessagesConstants.ID_ERR);
					}
				} else {
					throw new ValidationException(ErrorMessagesConstants.DEAL_NOT_EXIST);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.INCENTIVE_FORMAT);
			}

		} else {
			throw new ValidationException(ErrorMessagesConstants.CAR_NOT_EXIST);
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
	public ResponseEntity<?> deleteDeals(@RequestParam final int dId, @RequestParam final String dealModel) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Deal deletion requested by Dealer ID: " + dId);
		}
		if (validatorService.dealExistsValidator(dealModel)) {
			if (validatorService.dealerIdValidator(dId)) {
				D_LOGGER.info("SUCCESS : Deleting deal");
				return response.body(dealerService.deleteDeals(dealModel));
			} else {
				throw new ValidationException(ErrorMessagesConstants.ID_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.DEAL_NOT_EXIST);
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
	public ResponseEntity<?> fetchAllDeals(@RequestParam final int dId) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Deal fetch requested by Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final List<Deals> allDeals = dealerService.fetchAllDeals();
			if (allDeals.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.NO_DEAL_AVL);
			} else {
				D_LOGGER.info("SUCCESS : Fetching all deals.");
				return response.body(allDeals);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> recordIncentive(@RequestParam final int dId, @RequestParam final long contactNo,
			@RequestParam final String custName, @RequestParam final String date, @RequestParam final String model) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Incentive recording requested by Dealer ID: " + dId);
		}
		final LocalDate bookDate = dealerService.dateConverter(date);
		if (bookDate != null && validatorService.dealDateValidator(bookDate)) {
			if (validatorService.dealerIdValidator(dId)) {
				if (validatorService.nameValidator(custName)) {
					if (validatorService.contactValidator(contactNo)) {
						if (validatorService.checkIfDealApproved(model)) {
							D_LOGGER.info("SUCCESS : Recording incentive.");
							return response.body(dealerService.recordIncentives(dId, contactNo, custName, bookDate, model));
						} else {
							throw new ValidationException(ErrorMessagesConstants.APPROVAL_ERR);
						}

					} else {
						throw new ValidationException(ErrorMessagesConstants.CONTACT_ERR);
					}
				} else {
					throw new ValidationException(ErrorMessagesConstants.NAME_FORMAT);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.ID_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.DATE_INVALID);
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
	public ResponseEntity<?> fetchCustomerById(@RequestParam final int dId) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching Customer Details by Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final List<Customer> customerById = dealerService.fetchCustomerRecordsById(dId);
			if (customerById.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.NO_CUST_AVL);
			} else {
				D_LOGGER.info("SUCCESS : Fetching customers.");
				return response.body(customerById);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> fetchCustomerByContact(@RequestParam final int dId, @RequestParam final long contact) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching Customer Records by Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			if (validatorService.contactValidator(contact)) {
				final List<Customer> customerByContact = dealerService.fetchCustomerRecordsByContact(contact);
				if (customerByContact.isEmpty()) {
					throw new FetchEmptyException(ErrorMessagesConstants.NO_CUST_AVL);
				} else {
					D_LOGGER.info("SUCCESS : Fetching customers.");
					return response.body(customerByContact);
				}
			} else {
				throw new ValidationException(ErrorMessagesConstants.CONTACT_ERR);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
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
	public ResponseEntity<?> fetchIncentiveRecordsind(@RequestParam final int dId) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching Incentive Records by Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final List<Incentive> incentiveRecords = dealerService.fetchIncentiveRecordsById(dId);
			if (incentiveRecords.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.NO_INC_AVL);
			} else {
				D_LOGGER.info("SUCCESS : Fetching incentive records.");
				return response.body(incentiveRecords);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
	
	@GetMapping("/dealer/logged/fetchAllCars")
	public ResponseEntity<?> fetchAllCars(@RequestParam final int dId){
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching all cars for Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final List<Car> allCars = dealerService.fetchAllCars();
			if(allCars.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.NO_CAR_AVL);
			}
			else {
				D_LOGGER.info("SUCCESS : Fetching all cars.");
				return response.body(allCars);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
	
	@GetMapping("/dealer/logged/fetchApprovedDeals")
	public ResponseEntity<?> fetchApprovedDeals(@RequestParam final int dId){
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching approved cars for Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final List<Deals> approvedDeals = dealerService.fetchApprovedDeals();
			if(approvedDeals.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.NO_DEAL_AVL);
			}
			else {
				D_LOGGER.info("SUCCESS : Fetching approved Deals.");
				return response.body(approvedDeals);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
	
	@GetMapping("/dealer/logged/fetchRejectedDeals")
	public ResponseEntity<?> fetchRejectedDeals(@RequestParam final int dId){
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching approved cars for Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final List<Deals> rejectedDeals = dealerService.fetchRejectedDeals();
			if(rejectedDeals.isEmpty()) {
				throw new FetchEmptyException(ErrorMessagesConstants.NO_DEAL_AVL);
			}
			else {
				D_LOGGER.info("SUCCESS : Fetching rejected Deals.");
				return response.body(rejectedDeals);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
	
	@GetMapping("/dealer/logged/getProfile")
	public ResponseEntity<?> fetchProfile(@RequestParam final int dId) {
		if(D_LOGGER.isInfoEnabled()) {
			D_LOGGER.info("Fetching profile of Dealer ID: " + dId);
		}
		if (validatorService.dealerIdValidator(dId)) {
			final Dealer dealer = dealerService.fetchDealer(dId);
			if(dealer.equals(null)) {
				throw new FetchEmptyException(ErrorMessagesConstants.ID_ERR);
			}
			else {
				D_LOGGER.info("SUCCESS : Fetching dealer.");
				return response.body(dealer);
			}
		} else {
			throw new ValidationException(ErrorMessagesConstants.ID_ERR);
		}
	}
	
}
