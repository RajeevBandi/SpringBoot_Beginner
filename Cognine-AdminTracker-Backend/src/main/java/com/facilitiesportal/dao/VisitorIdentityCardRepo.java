package com.facilitiesportal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.VisitorIdentityCard;



/**
 * Repository interface for managing Visitor Identity Card entities.
 */
@Repository
public interface VisitorIdentityCardRepo extends JpaRepository<VisitorIdentityCard, Long> {
	
	  /**
	   * Finds a visitor identity card by its ID.
	   * 
	   * @param id The ID of the visitor identity card to find.
	   * @return The visitor identity card with the matching ID, or null if not found.
	   */
	VisitorIdentityCard findById(long id);
	
	  /**
	   * Finds a visitor identity card by its physical ID and checks if it's active.
	   * 
	   * @param physicalId The physical ID of the visitor identity card to find.
	   * @return The active visitor identity card with the matching physical ID, or null if not found or inactive.
	   */
	@Query("SELECT vic FROM VisitorIdentityCard vic WHERE vic.physicalId = :physicalId AND vic.isActive = true")
	VisitorIdentityCard findByPhysicalId(String physicalId);

}
