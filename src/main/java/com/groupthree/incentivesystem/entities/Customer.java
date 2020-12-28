package com.groupthree.incentivesystem.entities;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "customer_records")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "customer_id")
	private int customerId;
	@Column(name = "customer_contact")
	private long customerContact;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "purchase_date")
	private LocalDate customerPurchaseDate;
	@Column(name = "purchased_manufacturer")
	private String purchasedCarManf;
	@Column(name = "purchased_model")
	private String purchasedCar;
	@Column(name = "dealer_id")
	private int dealerId;
	@Column(name = "total_cost")
	private long totalCost;

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

	public LocalDate getCustomerPurchaseDate() {
		return customerPurchaseDate;
	}

	public void setCustomerPurchaseDate(LocalDate customerPurchaseDate) {
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
