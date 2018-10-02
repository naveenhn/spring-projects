/**
 * 
 */
package com.sarvah.mbg.domain.mongo.aceproject;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author Shivu
 *
 */
@Document
public class LeadProject extends AbstractDocument {

	@NotNull(message = "Name cannot be null for a project")
	private String name;

	@NotNull(message = "ProjectType cannot be null for a project")
	private ProjectType type;

	@DBRef
	@NotNull(message = "SalesExecutive connot be null for a project")
	private User salesExecutive;

	@Indexed(unique = true)
	@DBRef
	@NotNull(message = "Owner connot be null for a project")
	private User owner;

	@NotNull(message = "Project stage connot be null for a project")
	private String projectStage;

	@NotNull(message = "Address connot be null for a project")
	private Address address;

	@NotNull(message = "Area name connot be null for a project")
	private String area;

	private String note;

	private String requirementNote;

	private String followupDate;

	@NotNull(message = "Contacted date connot be null for a project")
	private String contactedDate;

	@NotNull(message = "Zipcode connot be null for a project")
	private String zipcode;

	private String referencedBy;

	private boolean called;

	private String reasonForReject;

	private String status;

	private String customerResponse;

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
	public ProjectType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ProjectType type) {
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
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public boolean isCalled() {
		return called;
	}

	/**
	 * @param called
	 *            the called to set
	 */
	public void setCalled(boolean called) {
		this.called = called;
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
