package com.jdbc.demo.service;

import java.util.List;

import com.jdbc.demo.model.UserTest;

public interface IUserService {
	
	void addUser(UserTest user);
	
	void updateUser(UserTest user);
	
	void deleteUser(int id);
	
	UserTest getById(int id);
	
	List<UserTest> getAll();
	
	List<UserTest> getByCity(String city);
	
	List<UserTest> getByStatus(String status);
}
