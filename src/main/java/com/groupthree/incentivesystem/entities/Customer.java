package com.groupthree.incentivesystem.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_records")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;
	@Column(name = "customer_contact")
	private long customerContact;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "purchase_date")
	private Date customerPurchaseDate;
	@Column(name = "purchased_manufacturer")
	private String purchasedCarManf;
	@Column(name = "purchased_model")
	private String purchasedCar;
	@Column(name = "dealer_id")
	private int dealerId;
	@Column(name = "total_cost")
	private long totalCost;

	public Customer() {

	}

	public Customer(long customerContact, String customerName, Date customerPurchaseDate, String purchasedCarManf,
			String purchasedCar, int dealerId, long totalCost) {
		this.customerContact = customerContact;
		this.customerName = customerName;
		this.customerPurchaseDate = customerPurchaseDate;
		this.purchasedCarManf = purchasedCarManf;
		this.purchasedCar = purchasedCar;
		this.dealerId = dealerId;
		this.totalCost = totalCost;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public Date getCustomerPurchaseDate() {
		return customerPurchaseDate;
	}

	public void setCustomerPurchaseDate(Date customerPurchaseDate) {
		this.customerPurchaseDate = customerPurchaseDate;
	}

	public String getPurchasedCarManf() {
		return purchasedCarManf;
	}

	public void setPurchasedCarManf(String purchasedCarManf) {
		this.purchasedCarManf = purchasedCarManf;
	}

	public String getPurchasedCar() {
		return purchasedCar;
	}

	public void setPurchasedCar(String purchasedCar) {
		this.purchasedCar = purchasedCar;
	}

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public long getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerContact=" + customerContact + ", customerName="
				+ customerName + ", customerPurchaseDate=" + customerPurchaseDate + ", purchasedCarManf="
				+ purchasedCarManf + ", purchasedCar=" + purchasedCar + ", dealerId=" + dealerId + ", totalCost="
				+ totalCost + "]";
	}

}
