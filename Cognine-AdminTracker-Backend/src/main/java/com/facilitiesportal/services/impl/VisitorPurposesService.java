package com.facilitiesportal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.FacilitiesDropdownsRepo;
import com.facilitiesportal.model.FacilitiesDropdowns;
import com.facilitiesportal.services.IVisitorPurposesService;

/**
 * service class to manage visitor-purposes-related operations.
 */
@Service
public class VisitorPurposesService implements IVisitorPurposesService {

	public VisitorPurposesService() {
		super();
	}

	@Autowired
	private FacilitiesDropdownsRepo facilitiesDropdownsRepo;

	/**
	 * Retrieves a list of all the visitor-purposes.
	 * 
	 * @return A list of VisitorPurposes
	 */
	@Override
	public List<FacilitiesDropdowns> getAllVisitorPurposes() {
		return facilitiesDropdownsRepo.findByDropDownTypeAndIsActiveOrderByValue("purpose",true);
	}

	/**
	 * Save a new Visitor purposes.
	 * 
	 * @param newVisitorPurposes The new visitors-purposes to save.
	 * @return A recently added visitors purposes.
	 */
	@Override
	public FacilitiesDropdowns addVisitorPurpose(FacilitiesDropdowns newVisitorPurposes) {
		FacilitiesDropdowns savedVisitorPurposes = null;
		try {
			newVisitorPurposes.setDropDownType("purpose");
			savedVisitorPurposes = facilitiesDropdownsRepo.save(newVisitorPurposes);
		} catch (Exception e) {
			System.out.println(e);
		}
		return savedVisitorPurposes;
	}

}
