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
/**
 * 
 * @author Tejas
 *
 */
@Service
public class DealerService {

	private static final Logger DS_LOGGER = LoggerFactory.getLogger("CustomerService.class");
	private final String validationSuccess = "Validation Successful";

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

	/**
	 * This method converts the date string of format 'yyyy-mm-dd' to LocalDate
	 * type.
	 * 
	 * @param date Only parameter for the method, accepts the string date.
	 * @return LocalDate type of 'date'.
	 */
	public LocalDate dateConverter(final String date) {
		DS_LOGGER.info("Converting type string to date");
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(date, format);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * This method finds the incentive percent based on holiday. If a holiday falls
	 * on the day the car is brought, max incentive bonus (100%) from the range is
	 * given, if one day away, 75% bonus from the range is given, two days away then
	 * 50% and if three days away then 25% and if more than three days away, then
	 * the lowest incentive percent(0%) from the range is given.
	 * 
	 * @param date   First parameter for the method, accepts the LocalDate date.
	 * @param incMin Second parameter for the method, accepts the lower limit of the
	 *               incentive range.
	 * @param incMax Third parameter for the method, accepts the upper limit of the
	 *               incentive range.
	 * @return The final incentive bonus.
	 */
	public double incentiveFind(final LocalDate date, final int incMin, final int incMax) {
		DS_LOGGER.info("Holiday and Incentive range finder.");
		int count = -3;
		int max = 0;
		final List<Holidays> holidays = holidayRepo.findAll();
		for (int i = 0; i < 7; i++) {
			LocalDate checker = date.plusDays(count++);
			if (holidays.stream().anyMatch(h -> h.getHolidayDate().equals(checker.toString()))) {
				int days = date.until(checker).getDays();
				max = 4 - Math.abs(days);
				break;
			} else{
				continue;
			}
		}
		final int variableInc = incMax - incMin;
		final double inc = (double) (variableInc * max) / 4;
		return (double) incMin + inc;
	}

	/**
	 * This method is used to log in dealers
	 * 
	 * @param dId   First parameter for the method, accepts the dealer ID.
	 * @param dPass Second parameter for the method, accepts the dealer password.
	 * @return True if successful log in, else False.
	 */
	public boolean dealerLogin(final int dId, final String dPass) {
		if(DS_LOGGER.isInfoEnabled()) {
			DS_LOGGER.info(validationSuccess + "... Logging in!");
		}
		dealerObj = dealerRepo.findById(dId).get();
		return dealerObj.getDealerPass().equals(dPass);
	}

	/**
	 * This method is used to register dealers.
	 * 
	 * @param dName    First parameter for the method, accepts the dealer name.
	 * @param dContact Second parameter for the method, accepts the dealer contact
	 *                 number.
	 * @param dPass    Third parameter for the method, accepts the dealer password.
	 * @return Newly created Dealer
	 */
	public Dealer dealerRegistration(final String dName, final long dContact, final String dPass) {
		if(DS_LOGGER.isInfoEnabled()) {
			DS_LOGGER.info(validationSuccess + "... Registering!");
		}
		dealerObj = new Dealer(dName, dContact, dPass);
		return dealerRepo.saveAndFlush(dealerObj);
	}

	/**
	 * This method is used by logged-in dealers to create new deals.
	 * 
	 * @param dealModel      First parameter for the method, accepts the car model.
	 * @param incentiveRange Second parameter for the method, accepts the incentive
	 *                       range string.
	 * @return Newly created Deal
	 */
	public Deals createDeals(final String dealModel, final String incentiveRange) {
		if(DS_LOGGER.isInfoEnabled()) {
			DS_LOGGER.info(validationSuccess + "... Creating deal!");
		}
		carObj = carRepo.findById(dealModel).get();
		dealsObj = new Deals(carObj.getCarManufacturer(), dealModel, carObj.getCarBasePrice(), carObj.getCarMsp(),
				incentiveRange);
		return dealsRepo.saveAndFlush(dealsObj);
	}

	/**
	 * This method is used by logged-in dealers to redefine pre-existing deals.
	 * 
	 * @param dealModel      First parameter for the method, accepts the car model.
	 * @param incentiveRange Second parameter for the method, accepts the incentive
	 *                       range string.
	 * @return Redefined Deal
	 */
	public Deals redefineDeals(final String dealModel, final String incentiveRange) {
		if(DS_LOGGER.isInfoEnabled()) {
			DS_LOGGER.info(validationSuccess + "... Redefining pre-existing deal!");
		}
		carObj = carRepo.findById(dealModel).get();
		dealsObj = dealsRepo.findById(dealModel).get();
		dealsObj.setIncentiveRange(incentiveRange);
		dealsObj.setStatus("nil");
		return dealsRepo.saveAndFlush(dealsObj);
	}

	/**
	 * This method is used by logged-in dealers to delete an existing deal.
	 * 
	 * @param dealModel Only parameter for the method, accepts the car model.
	 * @return Deleted deal.
	 */
	public String deleteDeals(final String dealModel) {
		if(DS_LOGGER.isInfoEnabled()) {
			DS_LOGGER.info(validationSuccess + "... Deleting deal!");
		}
		dealsObj = dealsRepo.findById(dealModel).get();
		final String deal = dealsObj.toString();
		dealsRepo.delete(dealsObj);
		return "Deleted deal: " + deal;
	}

	/**
	 * This method is used by logged-in dealers to record incentive details.
	 * 
	 * @param dId       First parameter for this method, accepts the dealer ID.
	 * @param contactNo Second parameter for this method, accepts the customer
	 *                  contact number.
	 * @param custName  Third parameter for this method, accepts the customer name.
	 * @param date      Fourth parameter for this method, accepts the date.
	 * @param model     Fifth parameter for this method, accepts the car model.
	 * @return Creates incentive and customer records and updates incentive of the
	 *         dealer.
	 */
	public String recordIncentives(final int dId, final long contactNo, final String custName, final  LocalDate date, final String model) {
		if(DS_LOGGER.isInfoEnabled()) {
			DS_LOGGER.info(validationSuccess + "... Recording details!");
		}
		String result = "";
		dealsObj = dealsRepo.findById(model).get();
		dealerObj = dealerRepo.findById(dId).get();

		final String manufacturer = dealsObj.getDealManufacturer();
		final String incentiveRange = dealsObj.getIncentiveRange();
		final String dealerName = dealerObj.getDealerName();
		final long cost = dealsObj.getCarMsp();
		final long existingIncentive = dealerObj.getDealerIncentive();

		final int minInc = Integer.parseInt(incentiveRange.substring(0, incentiveRange.indexOf('-')));
		final int maxInc = Integer.parseInt(incentiveRange.substring(incentiveRange.indexOf('-') + 1));
		final double incPercent = this.incentiveFind(date, minInc, maxInc);
		final long incentiveGot = (long) (cost * incPercent / 100);

		dealerObj.setDealerIncentive(existingIncentive + incentiveGot);
		dealerRepo.saveAndFlush(dealerObj);
		result += "Dealer Information: " + dealerObj.toString() + "\n";
		incentiveObj = new Incentive(contactNo, custName, Date.valueOf(date), model, dId, dealerName, cost, incPercent,
				incentiveGot);
		incentiveRepo.saveAndFlush(incentiveObj);
		result += "Incentive Information: " + incentiveObj.toString() + "\n";
		customerObj = new Customer(contactNo, custName, Date.valueOf(date), manufacturer, model, dId, cost);
		customerRepo.saveAndFlush(customerObj);
		result += "Customer Information: " + customerObj.toString() + "\n";
		return result;
	}

	/**
	 * This method is used to fetch all deals.
	 * 
	 * @return List of all deals
	 */
	public List<Deals> fetchAllDeals() {
		DS_LOGGER.info("List of All Deals");
		return dealsRepo.findAll();
	}

	/**
	 * This method is used to fetch all incentive records by dealer ID.
	 * 
	 * @param dId Only parameter for the record, accepts the dealer ID.
	 * @return List of incentive records for corresponding dealer.
	 */
	public List<Incentive> fetchIncentiveRecordsById(final int dId) {
		DS_LOGGER.info("Fetching Incentive Records for and ID");
		return incentiveRepo.findByDealerId(dId);
	}

	/**
	 * This method is used to fetch all customer records by dealer ID.
	 * 
	 * @param dId Only parameter for the method, accepts the dealer ID.
	 * @return List of customer records for corresponding dealer.
	 */
	public List<Customer> fetchCustomerRecordsById(final int dId) {
		DS_LOGGER.info("Fetching Customer Records by ID");
		return customerRepo.findByDealerId(dId);
	}

	/**
	 * This method is used to fetch all customer records by contact.
	 * 
	 * @param contact Only parameter for the method, accepts the customer contact.
	 * @return List of customer records for the corresponding contact.
	 */
	public List<Customer> fetchCustomerRecordsByContact(final long contact) {
		DS_LOGGER.info("Fetching Customer records by Contact ");
		return customerRepo.findByCustomerContact(contact);
	}
	
	public List<Car> fetchAllCars(){
		DS_LOGGER.info("Fetching all cars");
		return carRepo.findAll();
	}
	
	public List<Deals> fetchApprovedDeals(){
		DS_LOGGER.info("Fetching approved deals");
		return dealsRepo.findByDealStatus("Approved");
	}
	
	public List<Deals> fetchRejectedDeals(){
		DS_LOGGER.info("Fetching approved deals");
		return dealsRepo.findByDealStatus("Rejected");
	}

	public Dealer fetchDealer(int dId) {
		DS_LOGGER.info("Fetching dealer");
		return dealerRepo.findById(dId).get();
	}
}
