package com.jdbc.demo.dao.impl;

import com.jdbc.demo.dao.CustomerDAO;
import com.jdbc.demo.model.Customer;
import com.jdbc.demo.rowmapper.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("NamedParameterJdbcTemplate")
public class NamedParameterCustomerDAOImpl implements CustomerDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void addCustomer(Customer customer) {
    	System.out.println("NamedParameterJdbcTemplate");
        String sql = "INSERT INTO customer (id, name) VALUES (:id, :name)";
        Map<String, Object> params = new HashMap<>();
        params.put("id", customer.getId());
        params.put("name", customer.getName());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new CustomerRowMapper());
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        return namedParameterJdbcTemplate.query(sql, new CustomerRowMapper());
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name = :name WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("name", customer.getName());
        params.put("id", customer.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}

