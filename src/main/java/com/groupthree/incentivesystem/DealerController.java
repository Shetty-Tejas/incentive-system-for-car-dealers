package com.groupthree.incentivesystem;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Incentive;
import com.groupthree.incentivesystem.services.DealerService;
import com.groupthree.incentivesystem.services.ValidatorService;

/**
 * This is a Rest Controller for the Dealer usecases.
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
	 * @param dId First parameter for the method, accepts the Dealer ID.
	 * @param dPass Second parameter for the method, accepts the Dealer Password.
	 * @return 
	 */
	@PostMapping("/dealer/login")
	public String dealerLogin(@RequestParam int dId, @RequestParam String dPass) {
		logger.info("Dealer Login requested by Dealer ID: " + dId);
		if (validatorService.dealerIdValidator(dId) && validatorService.passValidator(dPass)) {
			if (dealerService.dealerLogin(dId, dPass))
				return "Dealer Login Successful!";
			else
				return "Wrong credentials.";
		} else
			return "Validation failed!";
	}

	/**
	 * This method is used to register the dealer.
	 * @param dName First parameter for the method, accepts the Dealer Name.
	 * @param dContact Second parameter for the method, accepts the Dealer contact number (10 digits at max and should start with 7, 8 or 9).
	 * @param dPass Third parameter for the method, accepts the Dealer Password (Alpha-numeric password with special characters like '$_.@' allowed.
	 * @return
	 */
	@PostMapping("/dealer/register")
	public Dealer dealerRegistration(@RequestParam String dName, @RequestParam long dContact,
			@RequestParam String dPass) {
		logger.info("Dealer Registration requested by Dealer Name: " + dName);
		if (validatorService.nameValidator(dName) && validatorService.contactValidator(dContact)
				&& validatorService.passValidator(dPass) && validatorService.checkIfContactExists(dContact)) {
			return dealerService.dealerRegistration(dName, dContact, dPass);
		} else
			return new Dealer(null, 0l, null);
	}

	/**
	 * This method is used by a logged in dealer to create deals.
	 * @param dId First parameter for the method, accepts the dealer ID by whom the deal is created. Used for logging purposes.
	 * @param dealModel Second parameter for the method, accepts the car model for which the deal is created. Should be available in the car repository.
	 * @param incentiveRange Third parameter for the method, accepts the incentive percent range, which is in the format "{min}-{max}" where min and max are percent between 1 - 100. 
	 * @return
	 */
	@PostMapping("/dealer/logged/createDeals")
	public Deals createDeals(@RequestParam int dId, @RequestParam String dealModel, @RequestParam String incentiveRange) {
		logger.info("Deal creation requested by Dealer ID: " + dId);
		if (validatorService.carExistsValidator(dealModel) && validatorService.incentiveRangeValidator(incentiveRange)
				&& !validatorService.dealExistsValidator(dealModel) && validatorService.dealerIdValidator(dId)) {
			return dealerService.createDeals(dealModel, incentiveRange);
		} else
			return new Deals(null, null, 0l, 0l, null);
	}

	/**
	 * This method is used by a logged in dealer to redefine the previous deals if those are rejected by the manufacturer.
	 * @param dId First parameter for the method, accepts the dealer ID by whom the deal is redefined. Used for logging purposes.
	 * @param dealModel Second parameter for the method, accepts the car model for which the deal is created. Should be available in the deal repository.
	 * @param incentiveRange Third parameter for the method, accepts the incentive percent range, which is in the format "{min}-{max}" where min and max are percent between 1 - 100.
	 * @return
	 */
	@PostMapping("/dealer/logged/redefineDeals")
	public Deals redefineDeals(@RequestParam int dId, @RequestParam String dealModel, @RequestParam String incentiveRange) {
		logger.info("Deal redefinition requested by Dealer ID: " + dId);
		if (validatorService.carExistsValidator(dealModel) && validatorService.incentiveRangeValidator(incentiveRange)
				&& validatorService.dealExistsValidator(dealModel) && validatorService.dealerIdValidator(dId)) {
			return dealerService.redefineDeals(dealModel, incentiveRange);
		} else
			return new Deals(null, null, 0l, 0l, null);
	}

	/**
	 * This method is used by a logged in dealer to delete unwanted incentive deals.
	 * @param dId First parameter for the method, accepts the dealer ID by whom the deal is deleted. Used for logging purposes.
	 * @param dealModel Second parameter for the method, used to delete the deal for inserted model. Should be available in the deal repository
	 * @return
	 */
	@PostMapping("/dealer/logged/deleteDeals")
	public String deleteDeals(@RequestParam int dId, @RequestParam String dealModel) {
		logger.info("Deal deletion requested by Dealer ID: " + dId);
		if (validatorService.dealExistsValidator(dealModel)) {
			return dealerService.deleteDeals(dealModel);
		} else
			return "Deal not found.";
	}

	/**
	 * This method is used by a logged in dealer to fetch all deals from the deal repository.
	 * @param dId First parameter for the method, accepts the dealer ID by whom all the deals are fetched. Used for logging purposes.
	 * @return
	 */
	@GetMapping("/dealer/logged/fetchAllDeals")
	public List<Deals> fetchAllDeals(@RequestParam int dId) {
		logger.info("Deal fetch requested by Dealer ID: " + dId);
		return dealerService.fetchAllDeals();
	}

	/**
	 * This method is used by a logged in dealer to record incentive details of the sold cars. This method also records Customer details and adds the incentive earned to the dealer's account.
	 * @param dId First parameter for the method, accepts the dealer ID by whom the car is sold.
	 * @param contactNo Second parameter for the method, accepts the customer contact number by whom the car is brought (10 digits at max and should start with 7, 8 or 9).
	 * @param custName Third parameter for the method, accepts the customer name by whom the car is brought.
	 * @param date Fourth parameter for the method, accepts the date at which the car is brought. The date can't be in the future.
	 * @param model Fifth parameter for the method, accepts the car model which is brought by the customer. The status of the car in deal repository should be approved.
	 * @return
	 */
	@PostMapping("/dealer/logged/recordIncentive")
	public String recordIncentive(@RequestParam int dId, @RequestParam long contactNo, @RequestParam String custName,
			@RequestParam String date, @RequestParam String model) {
		logger.info("Recording Incentives");
		LocalDate bookDate = dealerService.dateConverter(date);
		if (!bookDate.equals(null)) {
			if (validatorService.dealDateValidator(bookDate) && validatorService.dealerIdValidator(dId)
					&& validatorService.nameValidator(custName) && validatorService.contactValidator(contactNo)
					&& validatorService.checkIfDealApproved(model))
				return dealerService.recordIncentives(dId, contactNo, custName, bookDate, model);
			else
				return "Validation went wrong!";
		} else
			return "Date validation went wrong!";
	}

	/**
	 * This method is used by a logged in dealer to fetch all his/her customer details.
	 * @param dId Only parameter for the method, accepts the dealer ID to fetch the cars sold by the dealer.
	 * @return
	 */
	@GetMapping("/dealer/logged/fetchCustomerById")
	public List<Customer> fetchCustomerById(int dId) {
		logger.info("Fetching Customer Details by ID");
		if (validatorService.dealerIdValidator(dId))
			return dealerService.fetchCustomerRecordsById(dId);
		else
			return new LinkedList<>();
	}

	/**
	 * This method is used by a logged in dealer to fetch a customer details by his/her contact number.
	 * @param contact Only parameter for the method, accepts the customer contact to fetch the customer's details.
	 * @return
	 */
	@GetMapping("/dealer/logged/fetchCustomerByContact")
	public List<Customer> fetchCustomerByContact(long contact) {
		logger.info("Fetching Customer Records by Contact");
		if (validatorService.contactValidator(contact))
			return dealerService.fetchCustomerRecordsByContact(contact);
		else
			return new LinkedList<>();
	}
	
	/**
	 * This method is used by a logged in dealer to fetch all his/her incentive records.
	 * @param dId Only parameter for the method, accepts the dealer ID to fetch the incentive records.
	 * @return
	 */
		@GetMapping("/dealer/logged/fetchIncentiveRecords")
		public List<Incentive> fetchIncentiveRecordsind (int dId) {
			logger.info("Fetching Incentive Records by ID");
			if (validatorService.dealerIdValidator(dId))
				return dealerService.fetchIncentiveRecordsById(dId);
			else
				return new LinkedList<>();
		}
}
