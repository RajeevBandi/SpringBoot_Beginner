package com.facilitiesportal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Visitor")
public class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String firstname;

	private String lastname;

	@Column(name = "phonenum")
	private String phoneNum;

	@Column(name = "intime")
	private LocalTime inTime;
	
	@Column(name="outdatetime")
	private LocalDateTime outDateTime;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "comment", length = 500)
	private String comment;

	@Column(name = "createdat")
	@JsonIgnore
	private LocalDateTime createdAt;

	@Column(name = "updatedat")
	@JsonIgnore
	private LocalDateTime updatedAt;

	@Column(name = "createdby")
	@JsonIgnore
	private String createdBy;

	@Column(name = "updatedby")
	@JsonIgnore
	private String updatedBy;

	@Column(name = "isactive")
	@JsonIgnore
	private boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "dropdownid", referencedColumnName = "id")
	private FacilitiesDropdowns dropdown;

	@Column(name = "otherpurpose")
	private String otherPurpose;

	@Column(name = "countrycode")
	private String countryCode;

	@Column(name = "dialcode")
	private String dialCode;
	
	@ManyToOne
    @JoinColumn(name = "contactperson_empid", referencedColumnName = "id")
    @JsonIgnoreProperties({"email", "dateOfJoining", "jobTitle", "department", "reportsTo", "createdAt", "updatedAt", "newReportingHead", "active"})
    private Employee contactPersonEmp;
	
	@ManyToOne
	@JoinColumn(name="profileimageid", referencedColumnName = "id")
	@JsonIgnore
	private Image profileImageId;
	
	@Transient
    private String profileImageBase64;
	
	@Transient
	private List<String> idProofImagesBase64;
	
	@Column(name = "checkinmailmessageid")
	@JsonIgnore
	private String checkInMailMessageId;
	
	@Column(name = "checkoutmailmessageid")
	@JsonIgnore
	private String checkOutMailMessageId;

	@ManyToOne
	@JoinColumn(name = "visitoridentitycard_id", referencedColumnName = "id", nullable = true)
	private VisitorIdentityCard visitorIdentityCard;
	
	@Column(name="email", nullable = true)
	private String email;

	@Column(name="isvisitoridentitycardreturned ", nullable = true)
	private Boolean isVisitorIdentityCardReturned;
	
	@Column(name="othercontactperson_name", nullable = true)
	private String otherContactPersonName;
	
	public Visitor() {
		super();
	}

	public Visitor(String firstname, String lastname, String phoneNum, LocalTime inTime, LocalDateTime outDateTime,
			LocalDate date, String comment,  LocalDateTime createdAt, LocalDateTime updatedAt,
			String createdBy, String updatedBy, boolean isActive, FacilitiesDropdowns dropdown, String otherPurpose,
			String countryCode, String dialCode, Employee contactPersonEmp, Image profileImageId,
			String profileImageBase64, List<String> idProofImagesBase64, String checkInMailMessageId,
			String checkOutMailMessageId, VisitorIdentityCard visitorIdentityCard, String email,
			boolean isVisitorIdentityCardReturned, String otherContactPersonName) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.phoneNum = phoneNum;
		this.inTime = inTime;
		this.outDateTime = outDateTime;
		this.date = date;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.isActive = isActive;
		this.dropdown = dropdown;
		this.countryCode = countryCode;
		this.otherPurpose = otherPurpose;
		this.dialCode = dialCode;
		this.contactPersonEmp=contactPersonEmp;
		this.profileImageId=profileImageId;
		this.profileImageBase64=profileImageBase64;
		this.idProofImagesBase64 = idProofImagesBase64;
		this.checkInMailMessageId=checkInMailMessageId;
		this.checkOutMailMessageId=checkOutMailMessageId;
		this.visitorIdentityCard=visitorIdentityCard;
		this.email=email;
		this.isVisitorIdentityCardReturned=isVisitorIdentityCardReturned;
		this.otherContactPersonName=otherContactPersonName;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public LocalTime getInTime() {
		return inTime;
	}

	public void setInTime(LocalTime inTime) {
		this.inTime = inTime;
	}

	public LocalDateTime getOutDateTime() {
		return outDateTime;
	}

	public void setOutDateTime(LocalDateTime outDateTime) {
		this.outDateTime = outDateTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public FacilitiesDropdowns getDropdownObj() {
		return dropdown;
	}

	public void setDropdownObj(FacilitiesDropdowns dropdown) {
		this.dropdown = dropdown;
	}

	public String getOtherPurpose() {
		return otherPurpose;
	}

	public void setOtherPurpose(String otherPurpose) {
		this.otherPurpose = otherPurpose;
	}

	public String getDialCode() {
		return dialCode;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public Employee getContactPersonEmp() {
		return contactPersonEmp;
	}

	public void setContactPersonEmp(Employee contactPersonEmp) {
		this.contactPersonEmp = contactPersonEmp;
	}

	public Image getProfileImageId() {
		return profileImageId;
	}

	public void setProfileImageId(Image profileImageId) {
		this.profileImageId = profileImageId;
	}

	@JsonProperty("profileImageBase64") 
	public String getProfileImageBase64() {
		return profileImageBase64;
	}

	public void setProfileImageBase64(String profileImageBase64) {
		this.profileImageBase64 = profileImageBase64;
	}

	public List<String> getIdProofImagesBase64() {
		return idProofImagesBase64;
	}

	public void setIdProofImagesBase64(List<String> idProofImagesBase64) {
		this.idProofImagesBase64 = idProofImagesBase64;
	}

	public String getCheckInMailMessageId() {
		return checkInMailMessageId;
	}

	public void setCheckInMailMessageId(String checkInMailMessageId) {
		this.checkInMailMessageId = checkInMailMessageId;
	}

	public String getCheckOutMailMessageId() {
		return checkOutMailMessageId;
	}

	public void setCheckOutMailMessageId(String checkOutMailMessageId) {
		this.checkOutMailMessageId = checkOutMailMessageId;
	}

	public VisitorIdentityCard getVisitorIdentityCard() {
		return visitorIdentityCard;
	}

	public void setVisitorIdentityCard(VisitorIdentityCard visitorIdentityCard) {
		this.visitorIdentityCard = visitorIdentityCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsVisitorIdentityCardReturned() {
		return isVisitorIdentityCardReturned;
	}

	public void setIsVisitorIdentityCardReturned(Boolean isVisitorIdentityCardReturned) {
		this.isVisitorIdentityCardReturned = isVisitorIdentityCardReturned;
	}

	public String getOtherContactPersonName() {
		return otherContactPersonName;
	}

	public void setOtherContactPersonName(String otherContactPersonName) {
		this.otherContactPersonName = otherContactPersonName;
	}

}
