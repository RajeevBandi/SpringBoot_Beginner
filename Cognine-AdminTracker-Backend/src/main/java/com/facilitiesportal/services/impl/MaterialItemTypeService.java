package com.facilitiesportal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.FacilitiesDropdownsRepo;
import com.facilitiesportal.model.FacilitiesDropdowns;
import com.facilitiesportal.services.IMaterialItemTypeServiec;

/**
 * Service class for managing material item types.
 */
@Service
public class MaterialItemTypeService implements IMaterialItemTypeServiec{

	@Autowired
	private FacilitiesDropdownsRepo facilitiesDropdownsRepo;
	
	/**
     * Retrieves all material item types.
     *
     * @return A list of material item types.
     */
	@Override
	public List<FacilitiesDropdowns> getAllMaterialItemType() {
		return facilitiesDropdownsRepo.findByDropDownTypeAndIsActiveOrderByValue("material_type",true);
	}

	/**
     * Adds a new material item type.
     *
     * @param newMaterialItemType The new material item type to be added.
     * @return The saved material item type.
     */
	@Override
	public FacilitiesDropdowns addMaterialItemType(FacilitiesDropdowns newMaterialItemType) {
		
		FacilitiesDropdowns savedMaterialItemType = null;
		
		try {
			newMaterialItemType.setDropDownType("material_type");
			savedMaterialItemType = facilitiesDropdownsRepo.save(newMaterialItemType);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return savedMaterialItemType;
	}
}
