package com.facilitiesportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="facilities_portal_visitor_idproofimage_mapping")
public class Visitor_IdProofImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="visitorid", referencedColumnName = "id")
	private Visitor visitor;
	
	@ManyToOne
	@JoinColumn(name="imageid" , referencedColumnName = "id")
	private Image image;

	public Visitor_IdProofImage() {
		super();
	}

	public Visitor_IdProofImage(Visitor visitor, Image image) {
		super();
		this.visitor = visitor;
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
