package com.groupthree.incentivesystem.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupthree.incentivesystem.entities.Deals;
import com.groupthree.incentivesystem.repositories.DealsRepository;

@Service
public class CustomerService {

	@Autowired
	DealsRepository dealRepo;
	
	// Lists all approved deals
	public Map<String, Long> fetchApprovedDeals() {
		List<Deals> approvedDeals = dealRepo.findByDealStatus("Approved");
		return approvedDeals.stream().collect(Collectors.toMap(Deals::nameToString, Deals::getCarMsp));
	}
}
