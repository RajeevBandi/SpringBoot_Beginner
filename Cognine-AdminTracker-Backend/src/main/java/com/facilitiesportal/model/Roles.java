package com.facilitiesportal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String rolename;
	@Column
	private String displayrolename;
	@Column
	private String description;
	private Date createdat;
	private Date updatedat;
	@Column
	private boolean isactive;
	@Column
	private boolean projectrole;
	@Column
	private boolean globalrole;
	@Column
	private int priorityorder;

	public Roles() {
		super();
	}

	public Roles(String rolename, String displayrolename, String description, Date createdat, Date updatedat,
			boolean isactive, boolean projectrole, boolean globalrole, int priorityorder) {
		super();
		this.rolename = rolename;
		this.displayrolename = displayrolename;
		this.description = description;
		this.createdat = createdat;
		this.updatedat = updatedat;
		this.isactive = isactive;
		this.projectrole = projectrole;
		this.globalrole = globalrole;
		this.priorityorder = priorityorder;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDisplayrolename() {
		return displayrolename;
	}

	public void setDisplayrolename(String displayrolename) {
		this.displayrolename = displayrolename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public boolean getIsActive() {
		return isactive;
	}

	public void setActive(boolean isactive) {
		this.isactive = isactive;
	}

	public boolean isProjectrole() {
		return projectrole;
	}

	public void setProjectrole(boolean projectrole) {
		this.projectrole = projectrole;
	}

	public boolean isGlobalrole() {
		return globalrole;
	}

	public void setGlobalrole(boolean globalrole) {
		this.globalrole = globalrole;
	}

	public int getPriorityorder() {
		return priorityorder;
	}

	public void setPriorityorder(int priorityorder) {
		this.priorityorder = priorityorder;
	}

}
