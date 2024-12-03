package com.facilitiesportal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.facilitiesportal.model.Image;
import com.facilitiesportal.model.Visitor_IdProofImage;


/**
 * Repository interface for managing Visitor_IdProofImageRepo entities
 */
@Repository
public interface Visitor_IdProofImageRepo  extends JpaRepository<Visitor_IdProofImage, Long>{
	
	  /**
	   * Finds all images (ID proof) associated with a visitor's ID.
	   * 
	   * @param visitorId The ID of the visitor whose ID proof images are to be retrieved.
	   * @return A list of Image objects representing the visitor's ID proof images, or an empty list if none are found.
	   */
	@Query("SELECT vi.image FROM Visitor_IdProofImage vi WHERE vi.visitor.id = :visitorId")
    List<Image> findImagesByVisitorId(@Param("visitorId") long visitorId);
}
