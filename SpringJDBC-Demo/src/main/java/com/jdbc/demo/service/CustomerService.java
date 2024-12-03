package com.jdbc.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jdbc.demo.dao.CustomerDAO;
import com.jdbc.demo.model.Customer;

@Service
public class CustomerService {

    
    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerService(@Qualifier("NamedParameterJdbcTemplate") CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerDAO.deleteCustomer(id);
    }
}
