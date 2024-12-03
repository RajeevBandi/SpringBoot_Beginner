package com.facilitiesportal.services;

import java.util.List;

import com.facilitiesportal.model.FacilitiesDropdowns;

/**
 * Service interface for managing visitor-purposes-relted operations.
 */
public interface IVisitorPurposesService {

	/**
	 * Retrieves a list of visitors purposes..
	 * 
	 * @return A list of visitorsPurposes..
	 */
	public List<FacilitiesDropdowns> getAllVisitorPurposes();

	/**
	 * Save a new Visitor purposes.
	 * 
	 * @param newVisitorPurposes The new visitors-purposes to save.
	 * @return A recently saved visitors purposes.
	 */
	public FacilitiesDropdowns addVisitorPurpose(FacilitiesDropdowns newVisitorPurposes);

}
