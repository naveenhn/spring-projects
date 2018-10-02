/**
 * 
 */
package com.sarvah.mbg.domain.mongo.aceproject;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class Project extends AbstractDocument {

	@NotNull(message = "Name cannot be null for a project")
	private String name;
	@NotNull(message = "Desc cannot be null for a project")
	private Description desc;
	private Double budget;

	@NotNull(message = "ProjectType cannot be null for a project")
	private ProjectType type;

	@NotNull(message = "Address cannot be null for a project")
	private Address address;

	// project owner/userid
	@NotNull(message = "User cannot be null for a project")
	@DBRef
	private User user;

	// optional the use may add the products from the market place or
	// he can just list it down from in the desc
	private Set<String> productIds;

	private String status;

	private boolean constructionNew;
	
	private String userType;
	
	private String requiredByDate;

	
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
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
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the budget
	 */
	public Double getBudget() {
		return budget;
	}

	/**
	 * @param budget
	 *            the budget to set
	 */
	public void setBudget(Double budget) {
		this.budget = budget;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the productIds
	 */
	public Set<String> getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds
	 *            the productIds to set
	 */
	public void setProductIds(Set<String> productIds) {
		this.productIds = productIds;
	}

	/**
	 * @return the constructionNew
	 */
	public boolean isConstructionNew() {
		return constructionNew;
	}

	/**
	 * @param constructionNew
	 *            the constructionNew to set
	 */
	public void setConstructionNew(boolean constructionNew) {
		this.constructionNew = constructionNew;
	}

	/**
	 * @return the requiredByDate
	 */
	public String getRequiredByDate() {
		return requiredByDate;
	}

	/**
	 * @param requiredByDate the requiredByDate to set
	 */
	public void setRequiredByDate(String requiredByDate) {
		this.requiredByDate = requiredByDate;
	}

}
