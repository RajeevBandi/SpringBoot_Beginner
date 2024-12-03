package com.jdbc.demo.dao.impl;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jdbc.demo.model.Customer;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Example 1: Using Statement to Fetch All Customers
    public List<Customer> getAllCustomersWithStatement() {
        return jdbcTemplate.execute((Connection connection) -> {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customer");
            return mapCustomers(rs);
        });
    }

    // Example 2: Using PreparedStatement to Fetch Customer by ID
    public Customer getCustomerById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM customer WHERE id = ?",
            new Object[]{id},
            customerRowMapper
        );
    }

    // Example 3: Using PreparedStatement to Insert a New Customer
    public int addCustomer(Customer customer) {
        return jdbcTemplate.update(
            "INSERT INTO customer (id , name) VALUES ( ? , ? )",
            customer.getId(),
            customer.getName()
        );
    }

//    // Example 4: Using CallableStatement to Call a Stored Procedure
//    public String getCustomerNameUsingStoredProcedure(int id) {
//        return jdbcTemplate.execute((Connection connection) -> {
//            CallableStatement callableStatement = connection.prepareCall("{? = call get_customer_name(?)}");
//            callableStatement.registerOutParameter(1, Types.VARCHAR);
//            callableStatement.setInt(2, id);
//            callableStatement.execute();
//            return callableStatement.getString(1);
//        });
//    }

    // Helper method to map ResultSet to List of Customers
    private List<Customer> mapCustomers(ResultSet rs) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customers.add(customer);
        }
        return customers;
    }

    // RowMapper for mapping ResultSet to Customer
    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        return customer;
    };
}
