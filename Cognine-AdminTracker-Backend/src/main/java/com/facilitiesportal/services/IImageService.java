package com.facilitiesportal.services;

import java.util.Map;

/**
 * Service interface for managing operations related to visitor images.
 */
public interface IImageService {
	
	/**
     * Retrieves the profile image of a visitor.
     *
     * @param visitorId The ID of the visitor.
     * @return A map containing the profile image base64 string.
     */
	public Map<String, Object> getVisitorProfileImage(long visitorId);
	
	/**
     * Retrieves all ID proof images associated with a visitor.
     *
     * @param visitorId The ID of the visitor.
     * @return A map containing the ID proof images base64 strings.
     */
	public Map<String, Object> getVisitorIdProofImages(long visitorId);

}
