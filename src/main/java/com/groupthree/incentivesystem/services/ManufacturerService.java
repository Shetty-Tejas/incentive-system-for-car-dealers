package com.groupthree.incentivesystem.services;

import java.util.List;

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
	Manufacturer mObj;
	Car cObj;
	Deals dObj;
	@Autowired
	ManufacturerRepository manRepo;
	@Autowired
	CarRepository carRepo;
	@Autowired
	DealsRepository dealsRepo;

	private final String passwordPattern = "^[\\w|_|$|\\.|@]+$";
	private final String namePattern = "^[a-zA-Z|\\s]{3,34}$";
	private final String emailPattern = "^[\\w]+@[a-z]+\\.[a-z]{2,5}&";

	// Login Validator
	public boolean validator(int mId, String password) {
		if (password.matches(passwordPattern) && manRepo.existsById(mId))
			return true;
		else
			return false;
	}

	// Registration Validator
	public boolean validator(String mName, String mEmail, String password) {
		if (password.matches(passwordPattern) && mName.matches(namePattern) && mEmail.matches(emailPattern))
			return true;
		else
			return false;
	}

	// Insertion Validator
	public boolean validator(int mId, String carModel, long carBasePrice, long carMsp) {
		if (manRepo.existsById(mId) && carModel.matches("^\\w+$") && !carRepo.existsById(carModel)
				&& (carBasePrice < carMsp))
			return true;
		else
			return false;
	}

	// Insertion Validator mID only
	public boolean validator(int mId) {
		if (manRepo.existsById(mId))
			return true;
		else
			return false;
	}

	// Status Update Validator
	public boolean validator(String carModel) {
		if (dealsRepo.existsById(carModel))
			return true;
		else
			return false;
	}

	// Login
	public boolean manufacturerLogin(int mId, String password) {
		mObj = manRepo.findById(mId).get();
		if (mObj.getManufacturerId() == mId && mObj.getManufacturerPass().equals(password))
			return true;
		else
			return false;
	}

	// Register
	public Manufacturer manufacturerRegister(String mName, String mEmail, String mPass) {
		mObj = new Manufacturer(mName, mEmail, mPass);
		return manRepo.save(mObj);
	}

	// Insert Car
	public Car insertCar(int mId, String carModel, long carBasePrice, long carMsp) {
		String manufacturer = fetchManufacturerName(mId);
		cObj = new Car(manufacturer, carModel, carBasePrice, carMsp);
		return carRepo.save(cObj);
	}

	// Deal Status Update
	public Deals updateDealStatus(String carModel, boolean flag) {
		dObj = dealsRepo.findById(carModel).get();
		String status = (flag) ? "Approved" : "Rejected";
		dObj.setStatus(status);
		return dealsRepo.save(dObj);
	}

	// Find all deals for a manufacturer
	public List<Deals> fetchAllDeals(int mId) {
		String manufacturer = fetchManufacturerName(mId);
		return dealsRepo.findByDealManufacturer(manufacturer);
	}

	// Find manufacturer name
	public String fetchManufacturerName(int mId) {
		return manRepo.findById(mId).get().getManufacturerName();
	}
}
