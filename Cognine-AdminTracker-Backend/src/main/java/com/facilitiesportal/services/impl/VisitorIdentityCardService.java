package com.facilitiesportal.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilitiesportal.dao.VisitorIdentityCardRepo;
import com.facilitiesportal.model.VisitorIdentityCard;
import com.facilitiesportal.services.IVisitorIdentityCardService;
import com.facilitiesportal.util.SecurityUtil;
import com.facilitiesportal.util.VisitorIdentityCardStatus;

/**
 * Service class for managing visitor identity card operations.
 */
@Service
public class VisitorIdentityCardService implements IVisitorIdentityCardService{
	
	@Autowired
	private VisitorIdentityCardRepo visitorIdentityCardRepo;
	
	@Autowired
	private SecurityUtil securityUtil;
	
    /**
     * Retrieves a VisitorIdentityCard object from the database based on its ID.
     *
     * @param visitorIdentityCard_id The unique identifier of the visitor identity card to retrieve.
     * @return The VisitorIdentityCard object with the matching ID, or null if not found.
     */
	@Override
	public VisitorIdentityCard getVisitorIdentityCard(long visitorIdentityCard_id) {
		try {
			return visitorIdentityCardRepo.findById(visitorIdentityCard_id);
			
		}
		catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Error saving Visitor Identity Card ", e);		}
	}
	
	  /**
	   * Saves a new visitor identity card.
	   * 
	   * @param newVisitorIdentityCard The new visitor identity card to be saved.
	   * @return The saved visitor identity card object, or null if there's an error.
	   * @throws RuntimeException If an error occurs while saving the visitor identity card.
	   */
	@Override
	public VisitorIdentityCard saveVisitorIdentityCard(VisitorIdentityCard newVisitorIdentityCard) {
		VisitorIdentityCard savedIdentityCard = null;
		try {
			
			newVisitorIdentityCard.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			newVisitorIdentityCard.setCreatedBy(securityUtil.getLoggedInUsername());
			newVisitorIdentityCard.setIsActive(true);
			newVisitorIdentityCard.setIsAvailable(true);
			
			savedIdentityCard = visitorIdentityCardRepo.save(newVisitorIdentityCard);
		}
		catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Error saving Visitor Identity Card ", e);
		}
		return savedIdentityCard;
	}
	
	  /**
	   * Updates an existing visitor identity card.
	   * 
	   * @param updatedVisitorIdentityCard The visitor identity card containing the updated information.
	   * @return The updated visitor identity card object, or null if there's an error.
	   * @throws RuntimeException If an error occurs while updating the visitor identity card.
	   */
	@Override
	public VisitorIdentityCard updateVisitorIdentityCard(VisitorIdentityCard updatedVisitorIdentityCard) {
		VisitorIdentityCard savedIdentityCard = null;
		try {
			VisitorIdentityCard existingVisitorIdentityCard = visitorIdentityCardRepo.findById(updatedVisitorIdentityCard.getId());
			
			updatedVisitorIdentityCard.setCreatedAt(existingVisitorIdentityCard.getCreatedAt());
			updatedVisitorIdentityCard.setCreatedBy(existingVisitorIdentityCard.getCreatedBy());
			
			savedIdentityCard = visitorIdentityCardRepo.save(updatedVisitorIdentityCard);

		}
		catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Error updating Visitor Identity Card ", e);
		}
		return savedIdentityCard;
	}
	
	
	  /**
	   * Gets the status of a visitor identity card.
	   * 
	   * @param visitorIdentityCard The visitor identity card for which the status is requested.
	   * @return The status of the visitor identity card (AVAILABLE, UNAVAILABLE, or INVALID).
	   * @throws RuntimeException If an error occurs while getting the visitor identity card status.
	   */
	@Override
	public VisitorIdentityCardStatus getVisitorIdentityCardStatus(VisitorIdentityCard visitorIdentityCard) {
	    try {
	        VisitorIdentityCard activeVisitorIdentityCard = visitorIdentityCardRepo.findByPhysicalId(visitorIdentityCard.getPhysicalId());
	        if (activeVisitorIdentityCard == null) {
	            return VisitorIdentityCardStatus.INVALID;
	        } else {
	            if (activeVisitorIdentityCard.getIsAvailable()) {
	                return VisitorIdentityCardStatus.AVAILABLE;
	            } else {
	                return VisitorIdentityCardStatus.UNAVAILABLE;
	            }
	        }
		}		
		catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Error getting Visitor Identity Card Status", e);
		}
	}

}
