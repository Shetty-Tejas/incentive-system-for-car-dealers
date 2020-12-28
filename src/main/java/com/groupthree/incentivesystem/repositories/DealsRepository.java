package com.groupthree.incentivesystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Deals;

@Repository
public interface DealsRepository extends JpaRepository<Deals, String>{
	List<Deals> findByDealStatus(String dealStatus);
	List<Deals> findByDealManufacturer(String dealManufacturer);
}
