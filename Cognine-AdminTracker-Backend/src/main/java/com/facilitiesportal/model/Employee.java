package com.facilitiesportal.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "employeename", nullable = false)
	private String employeeName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "doj", nullable = true)
	private Date dateOfJoining;

	@Column(name = "jobtitle", nullable = true)
	private String jobTitle;

	@Column(name = "department", nullable = true)
	private String department;

	@Column(name = "reportsto")
	private String reportsTo;

	@Column(name = "isactive", nullable = true)
	private boolean isActive;

	@Column(name = "createdat", nullable = true)
	private Date createdAt;

	@Column(name = "updatedat", nullable = true)
	private Date updatedAt;

	@Column(name = "newreportinghead", nullable = true)
	private String newReportingHead;

	public Employee() {
		super();
	}

	public Employee(Integer id, String employeeName, String email, Date dateOfJoining, String jobTitle,
			String department, String reportsTo, boolean isActive, Date createdAt, Date updatedAt,
			String newReportingHead) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.email = email;
		this.dateOfJoining = dateOfJoining;
		this.jobTitle = jobTitle;
		this.department = department;
		this.reportsTo = reportsTo;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.newReportingHead = newReportingHead;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(String reportsTo) {
		this.reportsTo = reportsTo;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getNewReportingHead() {
		return newReportingHead;
	}

	public void setNewReportingHead(String newReportingHead) {
		this.newReportingHead = newReportingHead;
	}

}
