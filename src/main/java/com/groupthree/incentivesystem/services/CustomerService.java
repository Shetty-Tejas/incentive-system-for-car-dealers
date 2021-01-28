package com.groupthree.incentivesystem.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.repositories.DealsRepository;

/**
 * This is a Service class for the Customer usecases.
 * @author Snehal
 *
 */
@Service
public class CustomerService {
	
	private static final Logger CS_LOGGER = LoggerFactory.getLogger("CustomerService.class");

	@Autowired
	private DealsRepository dealRepo;
	
	/**
	 * Lists all approved deals
	 * @return Map of name and price of the car.
	 */
	public Map<String, Long> fetchApprovedDeals() {
		CS_LOGGER.info("Creating a List of Approved Deals");
		final List<Deals> approvedDeals = dealRepo.findByDealStatus("Approved");
		return approvedDeals.stream().collect(Collectors.toMap(Deals::nameToString, Deals::getCarMsp));
	}
}
