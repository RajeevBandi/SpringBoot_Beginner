package com.facilitiesportal.model;


public class VisitorCheckoutSearchCriteria {
	
	private String firstName;
	private String lastName;
	private String phoneNum;
	
	public VisitorCheckoutSearchCriteria() {
		super();
	}

	public VisitorCheckoutSearchCriteria(String firstName, String lastName, String phoneNum) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
