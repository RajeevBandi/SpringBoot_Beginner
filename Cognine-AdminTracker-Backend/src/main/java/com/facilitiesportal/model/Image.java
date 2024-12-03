package com.facilitiesportal.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "facilities_portal_images")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @Column(name = "encodedimage", columnDefinition = "BYTEA")
    private byte[] encodedImage;
    
	@Column(name = "createdat")
	private LocalDateTime createdAt;

	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "isactive")
	private boolean isActive;
	
	
	public Image() {
		super();
	}

	public Image(byte[] encodedImage, LocalDateTime createdAt, String createdBy, boolean isActive) {
		super();
		this.encodedImage = encodedImage;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(byte[] encodedImage) {
		this.encodedImage = encodedImage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}