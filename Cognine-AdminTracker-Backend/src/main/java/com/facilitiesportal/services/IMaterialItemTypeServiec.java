package com.facilitiesportal.services;

import java.util.List;

import com.facilitiesportal.model.FacilitiesDropdowns;
/**
 * Service interface for managing material item types.
 */
public interface IMaterialItemTypeServiec {
	
	/**
     * Retrieves all material item types.
     *
     * @return A list of material item types.
     */
	public List<FacilitiesDropdowns> getAllMaterialItemType();
	
	/**
     * Adds a new material item type.
     *
     * @param newMaterialItemType The new material item type to be added.
     * @return The saved material item type.
     */
	public FacilitiesDropdowns addMaterialItemType(FacilitiesDropdowns newMaterialItemType);
}
