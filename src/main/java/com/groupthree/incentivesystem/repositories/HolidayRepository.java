package com.groupthree.incentivesystem.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupthree.incentivesystem.entities.Holidays;

@Repository
public interface HolidayRepository extends JpaRepository<Holidays, Integer>{
	public boolean existsByHolidayDate(String date);
	public Holidays findByHolidayDate(String date);
}
