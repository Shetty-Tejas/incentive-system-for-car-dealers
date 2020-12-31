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
	
	private final Logger logger = LoggerFactory.getLogger(ManufacturerService.class);	
	
	Manufacturer mObj;
	Car cObj;
	Deals dObj;
	@Autowired
	ManufacturerRepository manRepo;
	@Autowired
	CarRepository carRepo;
	@Autowired
	DealsRepository dealsRepo;

	// Login
	public boolean manufacturerLogin(int mId, String password) {
		mObj = manRepo.findById(mId).get();
		if (mObj.getManufacturerPass().equals(password)) {
			logger.info("Manufacturer Login Successful");
			return true;
		}
		else {
			logger.info("Manufacturer Login Unsuccessful ");
			return false;
		}
	}

	// Register
	public Manufacturer manufacturerRegister(String mName, String mEmail, String mPass) {
		logger.info("Manufaturer Registration");
		mObj = new Manufacturer(mName, mEmail, mPass);
		return manRepo.save(mObj);
	}

	// Insert Car
	public Car insertCar(int mId, String carModel, long carBasePrice, long carMsp) {
		if(!carRepo.existsById(carModel)) {
			logger.info("Insert Car Details");
			String manufacturer = fetchManufacturerName(mId);
			cObj = new Car(manufacturer, carModel, carBasePrice, carMsp);
			return carRepo.save(cObj);
		}
		else {
			logger.info("Car Deatils not inserted");
			return new Car(null, null, 0l, 0l);
		}
	}

	// Deal Status Update
	public Deals updateDealStatus(String carModel, boolean flag) {
		logger.info("Update Deal Status");
		dObj = dealsRepo.findById(carModel).get();
		String status = (flag) ? "Approved" : "Rejected";
		dObj.setStatus(status);
		return dealsRepo.save(dObj);
	}

	// Find all deals for a manufacturer
	public List<Deals> fetchAllDeals(int mId) {
		logger.info("Find all Deals or a manufacturer");
		String manufacturer = fetchManufacturerName(mId);
		return dealsRepo.findByDealManufacturer(manufacturer);
	}

	// Find manufacturer name
	public String fetchManufacturerName(int mId) {
		logger.info("Find Manufacturer Name by ID");
		return manRepo.findById(mId).get().getManufacturerName();
	}
	
	// Fetch all cars
	public List<Car> fetchAllCars(int mId){
		String manufacturer = fetchManufacturerName(mId);
		return carRepo.findByCarManufacturer(manufacturer);
	}
}
