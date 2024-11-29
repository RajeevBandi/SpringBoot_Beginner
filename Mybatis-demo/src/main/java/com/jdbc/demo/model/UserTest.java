package com.jdbc.demo.model;

public class UserTest {
	
	private int id;
	private String city;
	private String name;
	private String status;
	
	public UserTest() {
		super();
	}

	public UserTest(int id, String city, String name, String status) {
		super();
		this.id = id;
		this.city = city;
		this.name = name;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
	    return "UserTest{" +
	           "id=" + id +
	           ", city='" + city + '\'' +
	           ", name='" + name + '\'' +
	           ", status='" + status + '\'' +
	           '}';
	}

	

}
