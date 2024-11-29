package com.jdbc.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdbc.demo.model.UserTest;
import com.jdbc.demo.repository.IUserRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public void addUser(UserTest user) {
		userRepository.addUser(user);
	}

	@Override
	public void updateUser(UserTest user) {
		userRepository.updateUser(user);
	}

	@Override
	public void deleteUser(int id) {
		userRepository.deleteUser(id);
	}

	@Override
	public UserTest getById(int id) {
		return userRepository.getById(id);
	}

	@Override
	public List<UserTest> getAll() {
		return userRepository.getAll();
	}

	@Override
	public List<UserTest> getByCity(String city) {
		return userRepository.getByCity(city);
	}

	@Override
	public List<UserTest> getByStatus(String status) {
		return userRepository.getByStatus(status);
	}
}
