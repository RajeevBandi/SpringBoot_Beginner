package com.jpa.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.test.dao.UserRepo;
import com.jpa.test.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class UserService {
	

	@Autowired
	private UserRepo userRepository;

    @Autowired
    private EntityManager entityManager;

    // Method to get user by ID using stored procedure
//    public User getUserById(int userId) {
//        return userRepository.getUserById(userId);
//    }

    // Method to get user status using stored procedure (with output parameter)
    @Transactional
    public String getUserStatusById(int userId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("get_user_status_by_id");
        query.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("userStatus", String.class, ParameterMode.OUT);
        query.setParameter("userId", userId);

        query.execute();

        return (String) query.getOutputParameterValue("userStatus");
    }

}
