package com.jdbc.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jdbc.demo.dao.CustomerDAO;
import com.jdbc.demo.model.Customer;
import com.jdbc.demo.rowmapper.CustomerRowMapper;

@Repository("JdbcTemplate")
public class CustomerDAOImpl implements CustomerDAO {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;    

    @Override
    public void addCustomer(Customer customer) {
    	System.out.println("JdbcTemplate");
    	
        String sql = "INSERT INTO customer (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, customer.getId(), customer.getName());
    }

    @Override
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";	
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerRowMapper());
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getId());
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
