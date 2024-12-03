
package com.facilitiesportal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.Employee;

/**
 * Repository interface for managing Employee entities
 */
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	/**
	 * Find an employee by email.
	 * 
	 * @param email The email of the employee
	 * @return The Employee associated with the given email
	 */
	Employee findByEmail(String email);
	
	/**
	 * Finds an employee by their ID.
	 *
	 * @param id The ID of the employee to search for.
	 * @return The Employee entity corresponding to the provided ID, or null if not found.
	 */
	Employee findById(int id);	
	
	/**
	 * Retrieves the IDs and names of all employees, sorted by employee name.
	 *
	 * @return A list of maps containing employee IDs and names.
	 */
	@Query("SELECT e.id AS id, e.employeeName AS employeeName FROM Employee e WHERE e.isActive = true ORDER BY e.employeeName")
	List<Map<String, Object>> findIdAndEmployeeNameSortedByName();

	
	/**
	 * Finds an employee by their name.
	 *
	 * @param employeeName The name of the employee to search for.
	 * @return The Employee entity corresponding to the provided name, or null if not found.
	 */
	Employee findByEmployeeName(String employeeName);

}
