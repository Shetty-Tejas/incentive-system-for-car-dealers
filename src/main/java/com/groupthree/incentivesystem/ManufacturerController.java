package com.groupthree.incentivesystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupthree.incentivesystem.entities.Car;
import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.entities.Manufacturer;
import com.groupthree.incentivesystem.services.ManufacturerService;

@RestController
public class ManufacturerController {

	@Autowired
	ManufacturerService manufacturerService;

	@PostMapping("/manufacturer/login")
	public String manufacturerLogin(@RequestParam int mId, @RequestParam String mPass) {
		if (manufacturerService.validator(mId, mPass)) {
			if (manufacturerService.manufacturerLogin(mId, mPass))
				return "Logged in!";
			else
				return "Wrong credentials.";
		} else
			return "Validation failed!";
	}

	@PostMapping("/manufacturer/register")
	public Manufacturer manufacturerRegister(@RequestParam String mName, @RequestParam String mEmail,
			@RequestParam String mPass) {
		if (manufacturerService.validator(mName, mEmail, mPass)) {
			return manufacturerService.manufacturerRegister(mName, mEmail, mPass);
		} else
			return new Manufacturer(null, null, null);
	}

	@PostMapping("/manufacturer/logged/insertCar")
	public Car insertCar(@RequestParam int mId, @RequestParam String carModel, @RequestParam long carBasePrice,
			@RequestParam long carMsp) {
		if (manufacturerService.validator(mId, carModel, carBasePrice, carMsp)) {
			return manufacturerService.insertCar(mId, carModel, carBasePrice, carMsp);
		} else
			return new Car(null, null, 0l, 0l);
	}

	@PostMapping("/manufacturer/logged/alterStatus")
	public Deals alterStatus(@RequestParam String carModel, @RequestParam boolean flag) {
		if (manufacturerService.validator(carModel)) {
			return manufacturerService.updateDealStatus(carModel, flag);
		} else
			return new Deals(null, null, 0l, 0l, null, null);
	}

	@GetMapping("/manufacturer/logged/fetchAllDeals")
	public List<Deals> fetchAllDeals(@RequestParam int mId) {
		if (manufacturerService.validator(mId)) {
			return manufacturerService.fetchAllDeals(mId);
		} else
			return null;
	}

	@GetMapping("/manufacturer/logged/fetchAllCars")
	public List<Car> fetchAllCars(@RequestParam int mId) {
		if (manufacturerService.validator(mId)) {
			return manufacturerService.fetchAllCars(mId);
		} else
			return null;
	}

}
