package com.groupthree.incentivesystem.entities;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "Incentive_Records")
public class Incentive {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "incentive_id")
	private int incentiveId;
	@Column(name = "customer_contact")
	private long customerContact;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;
	@Column(name = "model_purchased")
	private String carModel;
	@Column(name = "dealer_id")
	private int dealerId;
	@Column(name = "dealer_name")
	private String dealerName;
	@Column(name = "total_cost")
	private long totalCost;
	@Column(name = "percent_received")
	private int incentivePercent;
	@Column(name = "incentive_received")
	private long incentiveGot;

	public int getIncentiveId() {
		return incentiveId;
	}

	public void setIncentiveId(int incentiveId) {
		this.incentiveId = incentiveId;
	}

	public long getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(long customerContact) {
		this.customerContact = customerContact;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}

	public int getIncentivePercent() {
		return incentivePercent;
	}

	public void setIncentivePercent(int incentivePercent) {
		this.incentivePercent = incentivePercent;
	}

	public long getIncentiveGot() {
		return incentiveGot;
	}

	public void setIncentiveGot(long incentiveGot) {
		this.incentiveGot = incentiveGot;
	}

	@Override
	public String toString() {
		return "Incentive [incentiveId=" + incentiveId + ", customerContact=" + customerContact + ", customerName="
				+ customerName + ", purchaseDate=" + purchaseDate + ", carModel=" + carModel + ", dealerId=" + dealerId
				+ ", dealerName=" + dealerName + ", totalCost=" + totalCost + ", incentivePercent=" + incentivePercent
				+ ", incentiveGot=" + incentiveGot + "]";
	}

}
