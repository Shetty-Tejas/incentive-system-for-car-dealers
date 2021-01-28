package com.groupthree.incentivesystem.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Manufacturer_Table")
public class Manufacturer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manufacturer_id")
	private int manufacturerId;
	@NotEmpty(message = "Manufacturer Name cannot be empty")
	@Column(name = "manufacturer_name")
	private String manufacturerName;
	@NotEmpty(message = "Manufacturer Email cannot be empty")
	@Column(name = "manufacturer_email")
	private String manufacturerEmail;
	@NotEmpty(message = "Manufacturer Password cannot be empty")
	@Column(name = "manufacturer_pass")
	private String manufacturerPass;

	public Manufacturer() {
	}

	public Manufacturer(String manufacturerName, String manufacturerEmail, String manufacturerPass) {
		this.manufacturerName = manufacturerName;
		this.manufacturerEmail = manufacturerEmail;
		this.manufacturerPass = manufacturerPass;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturerEmail() {
		return manufacturerEmail;
	}

	public void setManufacturerEmail(String manufacturerEmail) {
		this.manufacturerEmail = manufacturerEmail;
	}

	public String getManufacturerPass() {
		return manufacturerPass;
	}

	public void setManufacturerPass(String manufacturerPass) {
		this.manufacturerPass = manufacturerPass;
	}

	@Override
	public String toString() {
		return "Manufacturer [manufacturerId=" + manufacturerId + ", manufacturerName=" + manufacturerName
				+ ", manufacturerEmail=" + manufacturerEmail + ", manufacturerPass=" + manufacturerPass + "]";
	}

}
