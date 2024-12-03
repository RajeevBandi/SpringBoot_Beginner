
package com.facilitiesportal.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.facilitiesportal.services.IEmployeeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for managing employees.
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

	@Autowired
	IEmployeeService employeeServiceObj;

	/**
     * Retrieves the IDs and names of all employees.
     *
     * @return A list of maps containing employee IDs and names.
     */
	@GetMapping("/employees/id-and-names")
	@Secured({ "system_admin","office_admin", "security", "management" })
	public List<Map<String, Object>>  getAllEmployeeIdsAndNames() {
		return employeeServiceObj.getAllEmployeeIdsAndNames();	
		}
}
