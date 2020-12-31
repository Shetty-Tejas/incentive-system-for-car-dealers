package com.groupthree.incentivesystem.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Customer;
import com.groupthree.incentivesystem.entities.Dealer;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Holidays;
import com.groupthree.incentivesystem.entities.Incentive;
import com.groupthree.incentivesystem.repositories.CarRepository;
import com.groupthree.incentivesystem.repositories.CustomerRepository;
import com.groupthree.incentivesystem.repositories.DealerRepository;
import com.groupthree.incentivesystem.repositories.DealsRepository;
import com.groupthree.incentivesystem.repositories.HolidayRepository;
import com.groupthree.incentivesystem.repositories.IncentiveRepository;

@Service
public class DealerService {
	
	private static final Logger logger = LoggerFactory.getLogger("CustomerService.class");
	
	private Dealer dealerObj;
	private Car carObj;
	private Deals dealsObj;
	private Incentive incentiveObj;
	private Customer customerObj;

	@Autowired
	private DealerRepository dealerRepo;
	@Autowired
	private DealsRepository dealsRepo;
	@Autowired
	private CarRepository carRepo;
	@Autowired
	private IncentiveRepository incentiveRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private HolidayRepository holidayRepo;

	// Convert to date from string
	public LocalDate dateConverter(String date) {
		logger.info("Date Conversion");
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(date, format);
		}
		catch (Exception e) {
			return null;
		}
		
	}

	// Holiday and incentive range finder.
	public double incentiveFind(LocalDate date, int incMin, int incMax) {
		logger.info("Holiday and Incentive range finder.");
		int count = -3;
		int max = 0;
		List<Holidays> holidays = holidayRepo.findAll();
		for (int i = 0; i < 7; i++) {
			LocalDate checker = date.plusDays(count++);
			if (holidays.stream().anyMatch(h -> h.getHolidayDate().equals(checker.toString()))) {
				int p = date.until(checker).getDays();
				max = 4 - Math.abs(p);
				break;
			} else
				continue;
		}
		int variableInc = incMax - incMin;
		double inc = ((double) (variableInc * max) / 4);
		return (double) incMin + inc;
	}

	// Dealer Login
	public boolean dealerLogin(int dId, String dPass) {
		logger.info("Dealer Login");
		dealerObj = dealerRepo.findById(dId).get();
		if (dealerObj.getDealerPass().equals(dPass))
			return true;
		else
			return false;
	}

	// Dealer Registration
	public Dealer dealerRegistration(String dName, long dContact, String dPass) {
		logger.info("Dealer Registration");
		dealerObj = new Dealer(dName, dContact, dPass);
		return dealerRepo.save(dealerObj);
	}

	// Create Deals
	public Deals createDeals(String dealModel, String incentiveRange) {
		logger.info("Creating Deals");
		carObj = carRepo.findById(dealModel).get();
		dealsObj = new Deals(carObj.getCarManufacturer(), dealModel, carObj.getCarBasePrice(), carObj.getCarMsp(),
				incentiveRange);
		return dealsRepo.save(dealsObj);
	}

	// Redefine Deals
	public Deals redefineDeals(String dealModel, String incentiveRange) {
		logger.info("Redefining Deals");
		carObj = carRepo.findById(dealModel).get();
		dealsObj = dealsRepo.findById(dealModel).get();
		dealsObj.setIncentiveRange(incentiveRange);
		dealsObj.setStatus("nil");
		return dealsRepo.save(dealsObj);
	}

	// Delete Deals
	public String deleteDeals(String dealModel) {
		logger.info("Deleting Deals");
		dealsObj = dealsRepo.findById(dealModel).get();
		String deal = dealsObj.toString();
		dealsRepo.delete(dealsObj);
		return "Deleted deal: " + deal;
	}

	// Record Incentive
	public String recordIncentives(int dId, long contactNo, String custName, LocalDate date, String model) {
		String result = "";
		dealsObj = dealsRepo.findById(model).get();
		dealerObj = dealerRepo.findById(dId).get();

		long cost = dealsObj.getCarMsp();
		String manufacturer = dealsObj.getDealManufacturer();
		String incentiveRange = dealsObj.getIncentiveRange();
		String dealerName = dealerObj.getDealerName();
		long existingIncentive = dealerObj.getDealerIncentive();

		int minInc = Integer.parseInt(incentiveRange.substring(0, incentiveRange.indexOf('-')));
		int maxInc = Integer.parseInt(incentiveRange.substring(incentiveRange.indexOf('-') + 1));
		double incPercent = this.incentiveFind(date, minInc, maxInc);
		long incentiveGot = (long) (cost * incPercent / 100);

		dealerObj.setDealerIncentive(existingIncentive + incentiveGot);
		dealerRepo.save(dealerObj);
		result += "Dealer Information: " + dealerObj.toString() + "\n";
		incentiveObj = new Incentive(contactNo, custName, Date.valueOf(date), model, dId, dealerName, cost, incPercent,
				incentiveGot);
		incentiveRepo.save(incentiveObj);
		result += "Incentive Information: " + incentiveObj.toString() + "\n";
		customerObj = new Customer(contactNo, custName, Date.valueOf(date), manufacturer, model, dId, cost);
		customerRepo.save(customerObj);
		result += "Customer Information: " + customerObj.toString() + "\n";
		return result;
	}

	// Fetch all deals
	public List<Deals> fetchAllDeals() {
		logger.info("List of All Deals");
		return dealsRepo.findAll();
	}
	
	// Fetch incentive records for an id
	public List<Incentive> fetchIncentiveRecordsById(int dId) {
		logger.info("Fetching Incentive Records for and ID");
		return incentiveRepo.findByDealerId(dId);
	}
	
	// Fetch customer records for an id
	public List<Customer> fetchCustomerRecordsById(int dId) {
		logger.info("Fetching Customer Records by ID");
		return customerRepo.findByDealerId(dId);
	}
	
	// Fetch customer records by contact number of customer
	public List<Customer> fetchCustomerRecordsByContact(long contact) {
		logger.info("Fetching Customer records by Contact ");
		return customerRepo.findByCustomerContact(contact);
	}

}
