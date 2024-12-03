package com.jdbc.demo.dao;

import java.util.List;
import com.jdbc.demo.model.Customer;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    Customer getCustomerById(int id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
}

