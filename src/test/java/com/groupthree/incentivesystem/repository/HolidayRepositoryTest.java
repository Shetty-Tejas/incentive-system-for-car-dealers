package com.groupthree.incentivesystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupthree.incentivesystem.entities.Holidays;
import com.groupthree.incentivesystem.repositories.HolidayRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class HolidayRepositoryTest {

	@Autowired
	HolidayRepository holidayRepo;
	public Holidays getHolidays() {
		Holidays holidays = new Holidays();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate date = LocalDate.parse("yyyy-mm-dd" , format);
		holidays.setHolidayDate("2021-01-01");
		holidays.setHolidayName("New Year");
		return holidays;
	}
	
	@Test
	public void testExistsByHolidayDate() {
		Holidays holidays = getHolidays();
		Holidays savedInDb = holidayRepo.save(holidays);
		Holidays getFromDb = holidayRepo.getOne(2021-01-01);
		assertThat(getFromDb).isEqualTo(savedInDb);
	}
}
