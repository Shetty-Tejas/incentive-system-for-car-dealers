package com.groupthree.incentivesystem.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "car_table")
public class Car {
	@Column(name = "car_manufacturer")
	private String carManufacturer;
	@NotEmpty(message = "Car Model cannot be empty")
	@Id
	@Column(name = "car_model")
	private String carModel;
	@Column(name = "car_baseprice")
	private long carBasePrice;
	@Column(name = "car_msp")
	private long carMsp;

	public Car() {
	}

	public Car(String carManufacturer, String carModel, long carBasePrice, long carMsp) {
		this.carManufacturer = carManufacturer;
		this.carModel = carModel;
		this.carBasePrice = carBasePrice;
		this.carMsp = carMsp;
	}

	public String getCarManufacturer() {
		return carManufacturer;
	}

	public void setCarManufacturer(String carManufacturer) {
		this.carManufacturer = carManufacturer;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public long getCarBasePrice() {
		return carBasePrice;
	}

	public void setCarBasePrice(long carBasePrice) {
		this.carBasePrice = carBasePrice;
	}

	public long getCarMsp() {
		return carMsp;
	}

	public void setCarMsp(long carMsp) {
		this.carMsp = carMsp;
	}

	@Override
	public String toString() {
		return "Car [carManufacturer=" + carManufacturer + ", carModel=" + carModel + ", carBasePrice=" + carBasePrice
				+ ", carMsp=" + carMsp + "]";
	}

}
