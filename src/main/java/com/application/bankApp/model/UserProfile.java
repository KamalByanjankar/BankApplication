package com.application.bankApp.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="user_profile")
public class UserProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String address;
	private String city;
	private String zipCode;
	private String state;
	private Gender gender;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dob;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public UserProfile() {
		super();
	}

	public UserProfile(String address, String city, String zipCode, String state, Gender gender, LocalDate dob) {
		super();
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
		this.gender = gender;
		this.dob = dob;
	}
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", address=" + address + ", city=" + city + ", zipCode=" + zipCode + ", state="
				+ state + ", gender=" + gender + ", dob=" + dob + "]";
	}
	
	
}
