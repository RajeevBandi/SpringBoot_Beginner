package com.facilitiesportal.services;

import com.facilitiesportal.model.Visitor;

/**
 * Service interface for managing mail-relted operations.
 */
public interface IMailService {
	
	 /**
     * Sends a check-in mail to the contact person of a visitor.
     *
     * @param visitor The visitor object containing information about the visitor.
     * @return The message ID of the sent mail.
     */
	public String sendVisitorCheckInMail(Visitor visitor);
	
    /**
     * Sends a check-out mail to the contact person of a visitor, with a reference to the check-in mail.
     *
     * @param checkinMailMessageId The message ID of the check-in mail.
     * @param visitor              The visitor object containing information about the visitor.
     * @return The message ID of the sent mail.
     */
	public String sendVisitorCheckOutMail(String checkinMailMessageId, Visitor visitor);
}
