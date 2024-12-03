
package com.facilitiesportal.services;

import java.util.List;
import java.util.Map;

/**
 * Service interface for managing employees.
 */
public interface IEmployeeService {
	
	/**
     * Retrieves IDs and names of all employees.
     *
     * @return A list of object arrays containing employee IDs and names.
     */
    public List<Map<String, Object>> getAllEmployeeIdsAndNames();
    
    /**
     * Finds the ID of an employee by their email.
     *
     * @param email The email address of the employee to find.
     * @return The ID of the employee with the provided email, or 0 if not found.
     */
    public long findEmployeeIdByEmail(String email);
}
