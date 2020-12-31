package com.groupthree.incentivesystem.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Incentive_Records")
public class Incentive {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "incentive_id")
	private int incentiveId;
	@Column(name = "customer_contact")
	private long customerContact;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "purchase_date")
	private Date purchaseDate;
	@Column(name = "model_purchased")
	private String carModel;
	@Column(name = "dealer_id")
	private int dealerId;
	@Column(name = "dealer_name")
	private String dealerName;
	@Column(name = "total_cost")
	private long totalCost;
	@Column(name = "percent_received")
	private double incentivePercent;
	@Column(name = "incentive_received")
	private long incentiveGot;

	public Incentive() {

	}

	public Incentive(long customerContact, String customerName, Date purchaseDate, String carModel, int dealerId,
			String dealerName, long totalCost, double incentivePercent, long incentiveGot) {
		super();
		this.customerContact = customerContact;
		this.customerName = customerName;
		this.purchaseDate = purchaseDate;
		this.carModel = carModel;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.totalCost = totalCost;
		this.incentivePercent = incentivePercent;
		this.incentiveGot = incentiveGot;
	}

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

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
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

	public double getIncentivePercent() {
		return incentivePercent;
	}

	public void setIncentivePercent(double incentivePercent) {
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
