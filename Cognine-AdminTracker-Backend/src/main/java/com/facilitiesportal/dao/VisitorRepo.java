package com.facilitiesportal.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.Visitor;

/**
 * Repository interface for managing Visitor entities
 */
@Repository
public interface VisitorRepo extends JpaRepository<Visitor, Long> {
	
	/**
     * Finds a visitor by their ID.
     *
     * @param id The ID of the visitor to find.
     * @return The visitor with the specified ID, if found.
     */
	Visitor findById(long id);

	/**
	 * Searches visitors based on the provided criteria.
	 *
	 * @param keyword         The keyword to search for in visitor details.
	 * @param purposeId       The ID of the purpose for visiting.
	 * @param startDate       The start date of the visit.
	 * @param endDate         The end date of the visit.
	 * @param isDateRangeNull Boolean indicating if the date range is null.
	 * @param checkedInOnly   Boolean indicating if the search is limited to checked-in visitors only.
	 * @param pageable        Pagination information.
	 * @return A page of visitors matching the search criteria.
	 */
	@Query("SELECT v FROM Visitor v WHERE "
			+ " (:keyword IS NULL OR :keyword = '' OR "
			+ " (LOWER(v.firstname) LIKE %:keyword% OR " 
			+ " LOWER(v.lastname) LIKE %:keyword% OR "
			+ " LOWER(CONCAT(v.firstname, ' ', v.lastname)) LIKE %:keyword% OR "
			+ " LOWER(v.contactPersonEmp.employeeName) LIKE %:keyword% )) " 
			+ " AND "
			+ " (:purposeId IS NULL OR (v.dropdown.id = :purposeId)) " 
			+ " AND " 
			+ " (:isDateRangeNull = true OR "
			+ " ( v.date >= :startDate AND v.date <= :endDate))" 
			+ " AND "
			+ " (:checkedInOnly = false OR (v.inTime IS NOT NULL AND v.outDateTime IS NULL)) ")
	Page<Visitor> searchVisitors(@Param("keyword") String keyword, @Param("purposeId") Long purposeId,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
			@Param("isDateRangeNull") boolean isDateRangeNull, @Param("checkedInOnly") boolean checkedInOnly,
			Pageable pageable);

	/**
	 * Counts the number of visitors based on the provided criteria.
	 *
	 * @param keyword         The keyword to search for in visitor details.
	 * @param purposeId       The ID of the purpose for visiting.
	 * @param startDate       The start date of the visit.
	 * @param endDate         The end date of the visit.
	 * @param isDateRangeNull Boolean indicating if the date range is null.
	 * @param checkedInOnly   Boolean indicating if the search is limited to checked-in visitors only.
	 * @return The count of visitors matching the search criteria.
	 */
	@Query("SELECT COUNT(v) FROM Visitor v WHERE " 
			+ " (:keyword IS NULL OR :keyword = '' OR "
			+ " (LOWER(v.firstname) LIKE %:keyword% OR " 
			+ " LOWER(v.lastname) LIKE %:keyword% OR "
			+ " LOWER(CONCAT(v.firstname, ' ', v.lastname)) LIKE %:keyword% OR "
			+ " LOWER(v.contactPersonEmp.employeeName) LIKE %:keyword% )) " 
			+ " AND "
			+ " (:purposeId IS NULL OR (v.dropdown.id = :purposeId)) " 
			+ " AND " 
			+ " (:isDateRangeNull = true OR "
			+ " ( v.date >= :startDate AND v.date <= :endDate)) " 
			+ " AND "
			+ " (:checkedInOnly = false OR (v.inTime IS NOT NULL AND v.outDateTime IS NULL)) ")
	long countSearchVisitors(@Param("keyword") String keyword, @Param("purposeId") Long purposeId,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
			@Param("isDateRangeNull") boolean isDateRangeNull, @Param("checkedInOnly") boolean checkedInOnly);

	/**
	 * Finds visitors based on the provided search criteria for checkout purposes.
	 *
	 * @param firstName   The first name of the visitor to search for.
	 * @param lastName    The last name of the visitor to search for.
	 * @param phoneNumber The phone number of the visitor to search for.
	 * @return A list of visitors matching the search criteria.
	 */
	@Query("SELECT v FROM Visitor v WHERE " 
			+ "(:firstName IS NULL OR LOWER(v.firstname) = :firstName) "
			+ "AND (:lastName IS NULL OR LOWER(v.lastname) = :lastName) "
			+ "AND (:phoneNumber IS NULL OR v.phoneNum = :phoneNumber) " 
			+ "AND v.outDateTime IS NULL ")
	List<Visitor> findVisitorsByCheckoutSearchCriteria(@Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("phoneNumber") String phoneNumber);

	/**
	 * Counts the total number of visitors.
	 *
	 * @return The total count of visitors.
	 */
	long count();

}
