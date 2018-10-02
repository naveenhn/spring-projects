/**
 * 
 */
package com.sarvah.mbg.userprofile.ordermgmt.model;

import java.util.Date;

import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shivu
 *
 */
public class LeadProjectResponse {
	private String leadProjectId;
	private String name;
	private String type;
	private User salesExecutive;
	private User owner;
	private String projectStage;
	private Address address;
	private String area;
	private String note;
	private String requirementNote;
	private String followupDate;
	private String contactedDate;
	private Date createdDate;
	private String referencedBy;
	private String called;
	private String reasonForReject;
	private String status;
	private int lastOrderDays;
	private String customerResponse;

	/**
	 * @return the leadProjectId
	 */
	public String getLeadProjectId() {
		return leadProjectId;
	}

	/**
	 * @param leadProjectId
	 *            the leadProjectId to set
	 */
	public void setLeadProjectId(String leadProjectId) {
		this.leadProjectId = leadProjectId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the salesExecutive
	 */
	public User getSalesExecutive() {
		return salesExecutive;
	}

	/**
	 * @param salesExecutive
	 *            the salesExecutive to set
	 */
	public void setSalesExecutive(User salesExecutive) {
		this.salesExecutive = salesExecutive;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the projectStage
	 */
	public String getProjectStage() {
		return projectStage;
	}

	/**
	 * @param projectStage
	 *            the projectStage to set
	 */
	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the followupDate
	 */
	public String getFollowupDate() {
		return followupDate;
	}

	/**
	 * @param followupDate
	 *            the followupDate to set
	 */
	public void setFollowupDate(String followupDate) {
		this.followupDate = followupDate;
	}

	/**
	 * @return the contactedDate
	 */
	public String getContactedDate() {
		return contactedDate;
	}

	/**
	 * @param contactedDate
	 *            the contactedDate to set
	 */
	public void setContactedDate(String contactedDate) {
		this.contactedDate = contactedDate;
	}

	/**
	 * @return the requirementNote
	 */
	public String getRequirementNote() {
		return requirementNote;
	}

	/**
	 * @param requirementNote
	 *            the requirementNote to set
	 */
	public void setRequirementNote(String requirementNote) {
		this.requirementNote = requirementNote;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the reasonForReject
	 */
	public String getReasonForReject() {
		return reasonForReject;
	}

	/**
	 * @param reasonForReject
	 *            the reasonForReject to set
	 */
	public void setReasonForReject(String reasonForReject) {
		this.reasonForReject = reasonForReject;
	}

	/**
	 * @return the referencedBy
	 */
	public String getReferencedBy() {
		return referencedBy;
	}

	/**
	 * @param referencedBy
	 *            the referencedBy to set
	 */
	public void setReferencedBy(String referencedBy) {
		this.referencedBy = referencedBy;
	}

	/**
	 * @return the called
	 */
	public String getCalled() {
		return called;
	}

	/**
	 * @param called
	 *            the called to set
	 */
	public void setCalled(String called) {
		this.called = called;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the lastOrderDays
	 */
	public int getLastOrderDays() {
		return lastOrderDays;
	}

	/**
	 * @param lastOrderDays
	 *            the lastOrderDays to set
	 */
	public void setLastOrderDays(int lastOrderDays) {
		this.lastOrderDays = lastOrderDays;
	}

	/**
	 * @return the customerResponse
	 */
	public String getCustomerResponse() {
		return customerResponse;
	}

	/**
	 * @param customerResponse
	 *            the customerResponse to set
	 */
	public void setCustomerResponse(String customerResponse) {
		this.customerResponse = customerResponse;
	}
}
