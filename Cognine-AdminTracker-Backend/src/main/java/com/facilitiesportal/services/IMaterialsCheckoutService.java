package com.facilitiesportal.services;

import java.util.List;
import java.util.Map;

import com.facilitiesportal.model.ItemsReturned;
import com.facilitiesportal.model.MaterialCheckoutDTO;
import com.facilitiesportal.model.MaterialCheckoutSearchCriteria;

/**
 * Service interface for managing material checkouts operations.
 */
public interface IMaterialsCheckoutService {
	
	/**
	 * Adds material checkouts based on the provided MaterialCheckoutDTO and user email.
	 * 
	 * @param newMaterialsCheckout The MaterialCheckoutDTO containing the material checkout data.
	 * @param email                The email of the authenticated user.
	 * @return The MaterialCheckoutDTO with added material checkouts.
	 */
	public MaterialCheckoutDTO  addMaterialsCheckouts(MaterialCheckoutDTO newMaterialsCheckout, String email);
	
	/**
	 * Retrieves all material checkout records based on the provided search criteria, pagination parameters, and sorting options.
	 * 
	 * @param searchCriteria The criteria used to filter the material checkout records.
	 * @param pageNum        The page number of the pagination.
	 * @param pageSize       The size of each page in the pagination.
	 * @return               A map containing the search result, which includes material checkout DTOs,
	 *                       and the count of gate passes matching the search criteria.
	 */
	public Map<String, Object> getAllMaterialCheckout(MaterialCheckoutSearchCriteria searchCriteria, int pageNum, int pageSize);
	
	/**
	 * Checks in multiple material checkout items based on the provided information.
	 * 
	 * @param itemsReturned An object containing information about the items being checked in, including their IDs and actual return date.
	 */
	public void checkinMultipleItems(ItemsReturned itemsReturned);
	
	/**
	 * Edits a material checkout record. This method allows updating both the material checkout gate pass and its associated items.
	 * 
	 * @param updatedMaterialCheckoutDTO A MaterialCheckoutDTO object containing the updated details for the material checkout.
	 * @return A MaterialCheckoutDTO object representing the saved material checkout record with updated information.
	 */
	public MaterialCheckoutDTO editMaterialCheckout(MaterialCheckoutDTO updatedMaterialCheckoutDTO);
	
	/**
	 * Retrieves all distinct employee ids and names who sent the material checkout.
	 * 
	 * @return A list of maps, each containing the distinct employee ID and name.
	 */
	public  List<Map<String, Object>> getDistinctSentByEmployeeIdsAndNames();
}
