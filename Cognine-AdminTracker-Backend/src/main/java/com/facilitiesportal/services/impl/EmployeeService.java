
package com.facilitiesportal.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.EmployeeRepo;
import com.facilitiesportal.services.IEmployeeService;

/**
 * service class for managing employees.
 */
@Service
public class EmployeeService implements IEmployeeService {

	private EmployeeService() {
		super();
	}

	@Autowired
	private EmployeeRepo employeeRepo;

	/**
     * Retrieves IDs and names of all employees.
     *
     * @return A list of object arrays containing employee IDs and names.
     */
    @Override
    public List<Map<String, Object>> getAllEmployeeIdsAndNames() {
    	List<Map<String, Object>> employees = null;

        try {
            employees = this.employeeRepo.findIdAndEmployeeNameSortedByName();
        } catch (Exception e) {
            System.out.print(e);
        }
        return employees;
    }
    
    
    /**
     * Finds the ID of an employee by their email.
     *
     * @param email The email address of the employee to find.
     * @return The ID of the employee with the provided email.
     */
    public long findEmployeeIdByEmail(String email) {
    	return this.employeeRepo.findByEmail(email).getId();
    }

}
