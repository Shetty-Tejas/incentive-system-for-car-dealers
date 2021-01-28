package com.groupthree.incentivesystem.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.repositories.CarRepository;
import com.groupthree.incentivesystem.repositories.DealsRepository;
import com.groupthree.incentivesystem.repositories.ManufacturerRepository;

@Service
public class ManufacturerService {
	
	private static final Logger MS_LOGGER = LoggerFactory.getLogger(ManufacturerService.class);
	
	private final String validationSuccess = "Validation Successful";
	
	private Manufacturer mObj;
	private Car cObj;
	private Deals dObj;
	@Autowired
	private ManufacturerRepository manRepo;
	@Autowired
	private CarRepository carRepo;
	@Autowired
	private DealsRepository dealsRepo;

	/**
	 * This method is used to log in a manufacturer.
	 * @param mId First parameter for the method, accepts the manufacturer ID.
	 * @param password Second parameter for the method, accepts the manufacturer password.
	 * @return True if successful log in, else False.
	 */
	public boolean manufacturerLogin(final int mId, final String password) {
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Logging in!");
		}
		mObj = manRepo.findById(mId).get();
		return mObj.getManufacturerPass().equals(password);
	}

	/**
	 * This method is used to register a manufacturer.
	 * @param mName First parameter for the method, accepts the manufacturer name.
	 * @param mEmail Second parameter for the method, accepts the manufacturer email.
	 * @param mPass Third parameter for the method, accepts the manufacturer password.
	 * @return Newly created manufacturer object.
	 */
	public Manufacturer manufacturerRegister(final String mName, final String mEmail, final String mPass) {
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Registering!");
		}
		mObj = new Manufacturer(mName, mEmail, mPass);
		return manRepo.saveAndFlush(mObj);
	}

	/**
	 * This method is used to insert a car.
	 * @param mId First parameter for the method, accepts the manufacturer ID.
	 * @param carModel Second parameter for the method, accepts the car model.
	 * @param carBasePrice Third parameter for the method, accepts the car base price.
	 * @param carMsp Fourth parameter for the method, accepts the car msp.
	 * @return Newly created car object.
	 */
	public Car insertCar(final int mId, final String carModel, final long carBasePrice, final long carMsp) {
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Inserting car!");
		}
		final String manufacturer = fetchManufacturerName(mId);
		cObj = new Car(manufacturer, carModel, carBasePrice, carMsp);
		return carRepo.saveAndFlush(cObj);
	}

	/**
	 * This method is used to update the status of a deal.
	 * @param carModel First parameter for the method, accepts the car model.
	 * @param flag Second parameter for the method, accepts either True or False.
	 * @return Updated Deal object
	 */
	public Deals updateDealStatus(final String carModel, final boolean flag) {
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Updating status!");
		}
		dObj = dealsRepo.findById(carModel).get();
		final String status = flag ? "Approved" : "Rejected";
		dObj.setStatus(status);
		return dealsRepo.saveAndFlush(dObj);
	}

	/**
	 * This method is used to fetch all deals for a manufacturer.
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return List of deals corresponding to the manufacturer.
	 */
	public List<Deals> fetchAllDeals(final int mId) {
		final String manufacturer = fetchManufacturerName(mId);
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Finding all deals for " + manufacturer);
		}
		return dealsRepo.findByDealManufacturer(manufacturer);
	}

	/**
	 * This method is used to fetch the manufacturer name for a manufacturer ID.
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return Name of the manufacturer
	 */
	public String fetchManufacturerName(final int mId) {
		return manRepo.findById(mId).get().getManufacturerName();
	}
	
	/**
	 * The method is used to fetch all cars for a manufacturer.
	 * @param mId Only parameter for the method, accepts the manufacturer ID.
	 * @return List of cars corresponding to the manufacturer.
	 */
	public List<Car> fetchAllCars(final int mId){
		final String manufacturer = fetchManufacturerName(mId);
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Finding all cars for " + manufacturer);
		}
		return carRepo.findByCarManufacturer(manufacturer);
	}
	
	public Manufacturer getProfile(final int mId) {
		if(MS_LOGGER.isInfoEnabled()) {
			MS_LOGGER.info(validationSuccess + "... Finding profile: " + mId);
		}
		return manRepo.findById(mId).get();
	}
}
