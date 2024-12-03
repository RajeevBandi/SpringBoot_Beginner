package com.facilitiesportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.FacilitiesDropdowns;

/**
 * Repository interface for managing VisitorPurposes entities
 */
@Repository
public interface FacilitiesDropdownsRepo extends JpaRepository<FacilitiesDropdowns, Long> {
	
    
    /**
     * Retrieves active facilities dropdowns by the specified drop-down type, sorted by the value.
     *
     * @param dropDownType The type of the drop-down.
     * @param isActive     A boolean indicating whether the facility is active or not.
     * @return A sorted list of active facilities dropdowns matching the given drop-down type.
     */
    List<FacilitiesDropdowns> findByDropDownTypeAndIsActiveOrderByValue(String dropDownType, boolean isActive);

}
