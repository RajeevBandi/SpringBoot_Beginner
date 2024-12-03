package com.facilitiesportal.services;

import com.facilitiesportal.model.VisitorIdentityCard;
import com.facilitiesportal.util.VisitorIdentityCardStatus;

/**
 * Interface defining methods for managing visitor identity card operations.
 */
public interface IVisitorIdentityCardService {
	
    /**
     * Retrieves a VisitorIdentityCard object from the database based on its ID.
     *
     * @param visitorIdentityCard_id The unique identifier of the visitor identity card to retrieve.
     * @return The VisitorIdentityCard object with the matching ID, or null if not found.
     */
	public VisitorIdentityCard getVisitorIdentityCard(long visitorIdentityCard_id);

	/**
	 * Saves a new visitor identity card. (Implementation details in implementing
	 * class)
	 * 
	 * @param newVisitorIdentityCard The new visitor identity card to be saved.
	 * @return The saved visitor identity card object.
	 */
	public VisitorIdentityCard saveVisitorIdentityCard(VisitorIdentityCard newVisitorIdentityCard);

	/**
	 * Updates an existing visitor identity card. (Implementation details in
	 * implementing class)
	 * 
	 * @param updatedVisitorIdentityCard The visitor identity card containing the
	 *                                   updated information.
	 * @return The updated visitor identity card object.
	 */
	public VisitorIdentityCard updateVisitorIdentityCard(VisitorIdentityCard updatedVisitorIdentityCard);


	/**
	 * Gets the status of a visitor identity card. (Implementation details in
	 * implementing class)
	 * 
	 * @param visitorIdentityCard The visitor identity card for which the status is
	 *                            requested.
	 * @return The status of the visitor identity card (AVAILABLE, UNAVAILABLE, or
	 *         INVALID).
	 */
	public VisitorIdentityCardStatus getVisitorIdentityCardStatus(VisitorIdentityCard visitorIdentityCard);

}
