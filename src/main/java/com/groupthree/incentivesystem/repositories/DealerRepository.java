package com.groupthree.incentivesystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Dealer;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Integer>{
	
}
