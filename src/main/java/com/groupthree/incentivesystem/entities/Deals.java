package com.groupthree.incentivesystem.entities;

import javax.persistence.*;

@Entity
@Table(name = "deals_table")
public class Deals {
	@Column(name = "deal_manufacturer")
	private String dealManufacturer;
	@Id
	@Column(name = "deal_model")
	private String dealModel;
	@Column(name = "car_baseprice")
	private long carBasePrice;
	@Column(name = "car_msp")
	private long carMsp;
	@Column(name = "incentive_range")
	private String incentiveRange;
	@Column(name = "deal_status")
	private String dealStatus;

	public Deals() {
	}

	public Deals(String dealManufacturer, String dealModel, long carBasePrice, long carMsp, String incentiveRange,
			String dealStatus) {
		this.dealManufacturer = dealManufacturer;
		this.dealModel = dealModel;
		this.carBasePrice = carBasePrice;
		this.carMsp = carMsp;
		this.incentiveRange = incentiveRange;
		this.dealStatus = dealStatus;
	}

	public String getDealManufacturer() {
		return dealManufacturer;
	}

	public void setDealManufacturer(String dealManufacturer) {
		this.dealManufacturer = dealManufacturer;
	}

	public String getDealModel() {
		return dealModel;
	}

	public void setDealModel(String dealModel) {
		this.dealModel = dealModel;
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

	public String getIncentiveRange() {
		return incentiveRange;
	}

	public void setIncentiveRange(String incentiveRange) {
		this.incentiveRange = incentiveRange;
	}

	public String getStatus() {
		return dealStatus;
	}

	public void setStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String nameToString() {
		return dealManufacturer + "-" + dealModel;
	}

	@Override
	public String toString() {
		return "Deals [dealManufacturer=" + dealManufacturer + ", dealModel=" + dealModel + ", carBasePrice="
				+ carBasePrice + ", carMsp=" + carMsp + ", incentiveRange=" + incentiveRange + ", dealStatus="
				+ dealStatus + "]";
	}

}
