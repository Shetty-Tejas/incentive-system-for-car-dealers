package com.groupthree.incentivesystem.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "dealer_table")
public class Dealer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dealer_id")
	private int dealerId;
	@NotEmpty(message = "Dealer Name cannot be empty")
	@Column(name = "dealer_name")
	private String dealerName;
	@Column(name = "dealer_contact")
	private long dealerContact;
	@NotEmpty(message = "Dealer Password cannot be empty")
	@Column(name = "dealer_pass")
	private String dealerPass;
	@Column(name = "dealer_incentive")
	private long dealerIncentive;

	public Dealer() {
	}

	public Dealer(String dealerName, long dealerContact, String dealerPass) {
		this.dealerName = dealerName;
		this.dealerContact = dealerContact;
		this.dealerPass = dealerPass;
		this.dealerIncentive = 0;
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

	public long getDealerContact() {
		return dealerContact;
	}

	public void setDealerContact(long dealerContact) {
		this.dealerContact = dealerContact;
	}

	public String getDealerPass() {
		return dealerPass;
	}

	public void setDealerPass(String dealerPass) {
		this.dealerPass = dealerPass;
	}

	public long getDealerIncentive() {
		return dealerIncentive;
	}

	public void setDealerIncentive(long dealerIncentive) {
		this.dealerIncentive = dealerIncentive;
	}

	@Override
	public String toString() {
		return "Dealer [dealerId=" + dealerId + ", dealerName=" + dealerName + ", dealerContact=" + dealerContact
				+ ", dealerPass=" + dealerPass + ", dealerIncentive=" + dealerIncentive + "]";
	}

}
