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

@RestController
public class DealerController {

	private static final Logger logger = LoggerFactory.getLogger("DealerController.class");
	
	@Autowired
	DealerService dealerService;
	@Autowired
	ValidatorService validatorService;

	// Dealer login
	@PostMapping("/dealer/login")
	public String dealerLogin(@RequestParam int dId, @RequestParam String dPass) {
		logger.info("Dealer Login");
		if (validatorService.dealerIdValidator(dId) && validatorService.passValidator(dPass)) {
			if (dealerService.dealerLogin(dId, dPass))
				return "Dealer Login Successful!";
			else
				return "Wrong credentials.";
		} else
			return "Validation failed!";
	}

	// Dealer Registration
	@PostMapping("/dealer/register")
	public Dealer dealerRegistration(@RequestParam String dName, @RequestParam long dContact,
			@RequestParam String dPass) {
		logger.info("Dealer Registration");
		if (validatorService.nameValidator(dName) && validatorService.contactValidator(dContact)
				&& validatorService.passValidator(dPass) && validatorService.checkIfContactExists(dContact)) {
			return dealerService.dealerRegistration(dName, dContact, dPass);
		} else
			return new Dealer(null, 0l, null);
	}

	// Creating Deals
	@PostMapping("/dealer/logged/createDeals")
	public Deals createDeals(@RequestParam String dealModel, @RequestParam String incentiveRange) {
		logger.info("Creating Deals");
		if (validatorService.carExistsValidator(dealModel) && validatorService.incentiveRangeValidator(incentiveRange)
				&& !validatorService.dealExistsValidator(dealModel)) {
			return dealerService.createDeals(dealModel, incentiveRange);
		} else
			return new Deals(null, null, 0l, 0l, null);
	}

	// Redefining deals
	@PostMapping("/dealer/logged/redefineDeals")
	public Deals redefineDeals(@RequestParam String dealModel, @RequestParam String incentiveRange) {
		logger.info("Redefining Deals");
		if (validatorService.carExistsValidator(dealModel) && validatorService.incentiveRangeValidator(incentiveRange)
				&& validatorService.dealExistsValidator(dealModel)) {
			return dealerService.redefineDeals(dealModel, incentiveRange);
		} else
			return new Deals(null, null, 0l, 0l, null);
	}

	// Deleting deals
	@PostMapping("/dealer/logged/deleteDeals")
	public String deleteDeals(@RequestParam String dealModel) {
		logger.info("Deleting Deals");
		if (validatorService.dealExistsValidator(dealModel)) {
			return dealerService.deleteDeals(dealModel);
		} else
			return "Deal not found.";
	}

	// Fetch all deals
	@GetMapping("/dealer/logged/fetchAllDeals")
	public List<Deals> fetchAllDeals() {
		logger.info("Fetching all Availabe Deals");
		return dealerService.fetchAllDeals();
	}

	// Record incentive
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

	// Fetch Customer records by id
	@GetMapping("/dealer/logged/fetchCustomerById")
	public List<Customer> fetchCustomerById(int dId) {
		logger.info("Fetching Customer Details by ID");
		if (validatorService.dealerIdValidator(dId))
			return dealerService.fetchCustomerRecordsById(dId);
		else
			return new LinkedList<>();
	}

	// Fetch Customer records by id
	@GetMapping("/dealer/logged/fetchCustomerByContact")
	public List<Customer> fetchCustomerByContact(long contact) {
		logger.info("Fetching Customer Records by Contact");
		if (validatorService.contactValidator(contact))
			return dealerService.fetchCustomerRecordsByContact(contact);
		else
			return new LinkedList<>();
	}
	
	// Fetch Incentive records by id
		@GetMapping("/dealer/logged/fetchIncentiveRecords")
		public List<Incentive> fetchIncentiveRecordsind (int dId) {
			logger.info("Fetching Incentive Records by ID");
			if (validatorService.dealerIdValidator(dId))
				return dealerService.fetchIncentiveRecordsById(dId);
			else
				return new LinkedList<>();
		}
}
