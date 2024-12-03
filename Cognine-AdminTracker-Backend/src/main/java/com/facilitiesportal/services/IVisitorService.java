package com.facilitiesportal.services;

import java.util.*;

import com.facilitiesportal.model.SearchOrSortCriteria;
import com.facilitiesportal.model.Visitor;
import com.facilitiesportal.model.VisitorCheckoutSearchCriteria;

/**
 * Service interface for managing visitor-relted operations.
 */
public interface IVisitorService {
	/**
	 * Retrieves a list of visitors based on the given parameters.
	 * 
	 * @param pageNum  The page number.
	 * @param pageSize The page size.
	 * @param role     The role of the user.
	 * @return A list of visitors.
	 */
	public List<Visitor> getAllVisitors(int pageNum, int pageSize);


	/**
	 * Retrieves a visitor by id.
	 * 
	 * @param visitorId the Id of the visitor.
	 * @return The Visitor.
	 */
	public Visitor getVisitorById(Long visitorId);

	/**
	 * Save a list of new Visitors.
	 * 
	 * @param newVisitors the list of new visitors to save.
	 * @return The list of recently saved visitors.
	 */
	public List<Visitor> saveVisitors(List<Visitor> newVisitors);

	/**
	 * Update a visitor with new details.
	 * 
	 * @param visitorId      The Id of the visitor to update
	 * @param visitorDetails The new details of visitor.
	 * @return The updated visitor.
	 */
	public Visitor updateVisitor(Long visitorId, Visitor visitorDetails);

	/**
	 * Delete a visitor by Id
	 * 
	 * @param visitorId The Id of the visitor to be delete.
	 * @return A map indicating weather the visitor was deleted successfully .
	 */
	public Map<String, Boolean> deleteVisitor(Long visitorId);

	/**
	 * Searches visitors based on the provided search criteria and returns a paginated list of matching visitors.
	 * 
	 * @param searchOrSortCriteria The search or sort criteria.
	 *  @param searchCheckedInVisitorsOnly Boolean indicating whether to search among checked-in visitors only.
	 * @param pageNum              The page number for pagination.
	 * @param pageSize             The page size for pagination.
	 * @return A map containing the paginated list of visitors matching the search criteria and the total count of search results.
	 */
	public Map<String, Object> searchVisitors(SearchOrSortCriteria searchOrSortCriteria,boolean searchCheckedInVisitorsOnly  , int pageNum, int pageSize);
	
	
	/**
	 * Retrieves the total count of visitors records.
	 * 
	 * @return The total count of visitors.
	 */
	public long totalVisitorsCount();

	
	/**
	 * Searches for visitors based on the provided criteria for the visitor checkout.
	 *
	 * @param visitorCheckoutSearchCriteria The criteria for searching visitors for checkout.
	 * @return A list of visitors matching the search criteria.
	 */
	public List<Visitor>searchVisitorsForCheckout(VisitorCheckoutSearchCriteria visitorCheckoutSearchCriteria);
	
	/**
	 * Retrieves the maximum number of ID proof images allowed for a visitor.
	 *
	 * @return A map containing the maximum number of ID proof images as the value, keyed by "maxIdProofImages".
	 */
	public Map<String, Integer> getMaxIdProofImages();

}
