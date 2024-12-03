package com.facilitiesportal.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facilitiesportal.model.Emp_FacilitiesPortalRoles;

/**
 * Repository interface for managing Employee Facilities Roles entities.
 */
@Repository
public interface Emp_FacilitiesPortalRolesRepo extends JpaRepository<Emp_FacilitiesPortalRoles, Long> {

	/**
	 * Find an Employee Facilities Role by employee email.
	 * 
	 * @param email The email of the employee
	 * @return The Employee Facilities Role associated with the given email
	 */
	Emp_FacilitiesPortalRoles findByEmployee_Email(String email);

	/**
	 * Find all Employee Facilities Roles by employee email.
	 * 
	 * @param email The email of the employee
	 * @return List of Employee Facilities Roles associated with the given email
	 */
	List<Emp_FacilitiesPortalRoles> findAllByEmployee_Email(String email);

}
