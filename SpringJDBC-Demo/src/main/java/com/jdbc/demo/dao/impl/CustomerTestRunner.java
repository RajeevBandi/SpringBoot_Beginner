package com.jdbc.demo.dao.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jdbc.demo.model.Customer;

@Component
public class CustomerTestRunner implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public CustomerTestRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Using Statement ===");
        customerRepository.getAllCustomersWithStatement().forEach(System.out::println);

        System.out.println("\n=== Using PreparedStatement (Insert) ===");
        Customer newCustomer = new Customer(7, "John Doe");
        int rowsInserted = customerRepository.addCustomer(newCustomer);
        System.out.println("Inserted rows: " + rowsInserted);

        System.out.println("\n=== Using PreparedStatement (Select by ID) ===");
        Customer customer = customerRepository.getCustomerById(1);
        System.out.println("Customer with ID 1: " + customer);

//        System.out.println("\n=== Using CallableStatement (Stored Procedure) ===");
//        String customerName = customerRepository.getCustomerNameUsingStoredProcedure(1);
//        System.out.println("Customer name from stored procedure: " + customerName);
    }
}
