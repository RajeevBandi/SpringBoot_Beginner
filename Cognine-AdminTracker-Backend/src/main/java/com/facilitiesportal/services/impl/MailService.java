package com.facilitiesportal.services.impl;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.facilitiesportal.model.Visitor;
import com.facilitiesportal.services.IMailService;
import com.facilitiesportal.util.ImageConverterUtil;
import com.nimbusds.jose.EncryptionMethod.Family;

/**
 * Service class for sending mails related to visitor check-in and check-out events.
 */
@Service
public class MailService implements IMailService {

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean starttls;

	@Value("${visitor.notification.mail.subject}")
	private String mailSubject;

	@Value("${visitor.comments}")
	private String mailComments;

	@Value("${visitor.check-in.mail.body}")
	private String checkinMailBody;

	@Value("${visitor.check-out.mail.body}")
	private String checkoutMailBody;
	
	@Value("${visitor.noticication.mail.ccRecipients:#{null}}")
	private String ccRecipients;
	
	@Value("${visitor.mail.image.template}")
	private String visitorImageHTML;
	
	@Value("${visitor.mail.signature}")
	private String signature;
	
	/**
     * Creates an SMTP session based on the configured properties.
     *
     * @return The SMTP session.
     */
	public Session createSmtpSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", String.valueOf(auth));
        properties.put("mail.smtp.starttls.enable", String.valueOf(starttls));

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
        
        return session;
	}

	 /**
     * Sends a check-in mail to the contact person of a visitor.
     *
     * @param visitor The visitor object containing information about the visitor.
     * @return The message ID of the sent mail.
     */
	@Override
	public String sendVisitorCheckInMail(Visitor visitor) {
		String messageId = null;
		try {

			MimeMessage checkinMail = new MimeMessage(createSmtpSession());

			String visitorName = visitor.getFirstname() + " " + visitor.getLastname();
			String purposeOfVisit;
			String visitorComments;

			if (visitor.getDropdownObj().getValue().equals("Others")) {
				purposeOfVisit = visitor.getOtherPurpose();
			}
			else {
				purposeOfVisit = visitor.getDropdownObj().getValue();
			}

			if (visitor.getComment() != null && visitor.getComment() != "") {
				visitorComments = mailComments.replace("{comments}", visitor.getComment());
			} else {
				visitorComments = "";
			}
			
			String htmlContent = "<html><body>";
			htmlContent += checkinMailBody.replace("{contactPerson}", visitor.getContactPersonEmp().getEmployeeName()).replace("{visitorsName}", visitorName) ;

			if (visitor.getProfileImageId() != null && visitor.getProfileImageId().getEncodedImage() != null) {
	
				String base64Image = "data:image/png;base64,"
						+ ImageConverterUtil.byteArrayToBase64(visitor.getProfileImageId().getEncodedImage());
				
				htmlContent += visitorImageHTML.replace("{visitorImageBase64}",base64Image);
			}
			
			htmlContent +=visitorComments;
			htmlContent+=signature;
			htmlContent += "</body></html>";

			checkinMail.setFrom(new InternetAddress(username));
			checkinMail.addRecipient(Message.RecipientType.TO,
					new InternetAddress(visitor.getContactPersonEmp().getEmail()));
			if (ccRecipients != null && !ccRecipients.isEmpty()) {
			    checkinMail.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccRecipients));
			}
			checkinMail.setSubject(mailSubject.replace("{visitorsName}", visitorName));
			
			checkinMail.setContent(htmlContent, "text/html");
			
			Transport.send(checkinMail);

			messageId = checkinMail.getHeader("Message-ID", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageId;
	}
	
    /**
     * Sends a check-out mail to the contact person of a visitor, with a reference to the check-in mail.
     *
     * @param checkinMailMessageId The message ID of the check-in mail.
     * @param visitor              The visitor object containing information about the visitor.
     * @return The message ID of the sent mail.
     */
	@Override
	public String sendVisitorCheckOutMail(String checkinMailMessageId, Visitor visitor) {
		String messageId=null;
		try {
			if(checkinMailMessageId!=null) {
				String visitorName = visitor.getFirstname() + " " + visitor.getLastname();
				String visitorComments;
				if (visitor.getComment() != null && visitor.getComment() != "") {
					visitorComments = mailComments.replace("{comments}", visitor.getComment());
				} else {
					visitorComments = "";
				}
				
		        String checkOutDate = visitor.getOutDateTime().format( DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
		        String checkOutTime = visitor.getOutDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));

				
				MimeMessage checkoutMail = new MimeMessage(createSmtpSession());
				checkoutMail.setFrom(new InternetAddress(username));
				checkoutMail.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(visitor.getContactPersonEmp().getEmail()));
				if (ccRecipients != null && !ccRecipients.isEmpty()) {
					checkoutMail.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccRecipients));
				}
				checkoutMail.setSubject("Re: "+mailSubject.replace("{visitorsName}", visitorName));
				
				String htmlContent = "<html><body>";
						
						htmlContent += checkoutMailBody.replace("{contactPerson}", visitor.getContactPersonEmp().getEmployeeName()).replace("{visitorsName}", visitorName).replace("{date}", checkOutDate).replace("{time}", checkOutTime);
						htmlContent +=  visitorComments ;
						htmlContent+=signature;
						htmlContent += "</body></html>";
						
						
						checkoutMail.setContent(htmlContent, "text/html");
				
				if (checkinMailMessageId != null) {
					checkoutMail.setHeader("In-Reply-To", checkinMailMessageId);
					checkoutMail.setHeader("References", checkinMailMessageId);
				}
				
			Transport.send(checkoutMail);
			messageId = checkinMailMessageId;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return messageId;
	}
}
