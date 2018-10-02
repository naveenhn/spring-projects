/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;

import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.contact.Address;

/**
 * @author Shiva
 *
 */
public class BidResponses {

	private String projectName;
	private String fullName;
	private Address address;
	private String description;
	private String firstName;
	private String lastName;
	private String biddingUserName;
	private Image userImage;
	private List<String> assetId;
	private String bidDescription;
	private Date biddedDate;
	private boolean constructionType;
	private ProjectType projectType;
	private Date postedDate;
	private double budget;

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the biddingUserName
	 */
	public String getBiddingUserName() {
		return biddingUserName;
	}

	/**
	 * @return the userImage
	 */
	public Image getUserImage() {
		return userImage;
	}

	/**
	 * @return the assetId
	 */
	public List<String> getAssetId() {
		return assetId;
	}

	/**
	 * @return the bidDescription
	 */
	public String getBidDescription() {
		return bidDescription;
	}

	/**
	 * @return the biddedDate
	 */
	public Date getBiddedDate() {
		return biddedDate;
	}

	/**
	 * @return the constructionType
	 */
	public boolean isConstructionType() {
		return constructionType;
	}

	/**
	 * @return the projectType
	 */
	public ProjectType getProjectType() {
		return projectType;
	}

	/**
	 * @return the postedDate
	 */
	public Date getPostedDate() {
		return postedDate;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param biddingUserName
	 *            the biddingUserName to set
	 */
	public void setBiddingUserName(String biddingUserName) {
		this.biddingUserName = biddingUserName;
	}

	/**
	 * @param userImage
	 *            the userImage to set
	 */
	public void setUserImage(Image userImage) {
		this.userImage = userImage;
	}

	/**
	 * @param assetId
	 *            the assetId to set
	 */
	public void setAssetId(List<String> assetId) {
		this.assetId = assetId;
	}

	/**
	 * @param bidDescription
	 *            the bidDescription to set
	 */
	public void setBidDescription(String bidDescription) {
		this.bidDescription = bidDescription;
	}

	/**
	 * @param biddedDate
	 *            the biddedDate to set
	 */
	public void setBiddedDate(Date biddedDate) {
		this.biddedDate = biddedDate;
	}

	/**
	 * @param constructionType
	 *            the constructionType to set
	 */
	public void setConstructionType(boolean constructionType) {
		this.constructionType = constructionType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	/**
	 * @param postedDate
	 *            the postedDate to set
	 */
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return budget;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}
}