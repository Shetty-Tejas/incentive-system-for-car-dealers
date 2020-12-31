package com.groupthree.incentivesystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Incentive;

@Repository
public interface IncentiveRepository extends JpaRepository<Incentive, Integer>{
	List<Incentive> findByDealerId(int id);
}
