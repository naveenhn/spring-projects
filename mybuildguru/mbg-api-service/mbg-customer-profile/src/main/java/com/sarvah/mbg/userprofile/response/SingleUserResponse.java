/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.Set;

import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author shivu
 *
 */
public class SingleUserResponse {
	private String id;
	private String username;
	private String phoneNumber;
	private String firstName;
	private String lastName;
	private String fullName;
	private String status;
	private String customerCode;
	private Date createdDate;
	private Date activeSince;
	private Description desc;
	private Image profilePicture;
	private Set<String> otherSkillSets;
	private Set<String> operatingCities;
	private String contactName;
	private boolean isVerified;
	private String packageName;
	private Double investedBalance;
	private Double currentBalance;
	private String title;
	private String vatNumber;

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the otherSkillSets
	 */
	public Set<String> getOtherSkillSets() {
		return otherSkillSets;
	}

	/**
	 * @param otherSkillSets
	 *            the otherSkillSets to set
	 */
	public void setOtherSkillSets(Set<String> otherSkillSets) {
		this.otherSkillSets = otherSkillSets;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the activeSince
	 */
	public Date getActiveSince() {
		return activeSince;
	}

	/**
	 * @param activeSince
	 *            the activeSince to set
	 */
	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
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
	 * @return the profilePicture
	 */
	public Image getProfilePicture() {
		return profilePicture;
	}

	/**
	 * @param profilePicture
	 *            the profilePicture to set
	 */
	public void setProfilePicture(Image profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * @return the operatingCities
	 */
	public Set<String> getOperatingCities() {
		return operatingCities;
	}

	/**
	 * @param operatingCities
	 *            the operatingCities to set
	 */
	public void setOperatingCities(Set<String> operatingCities) {
		this.operatingCities = operatingCities;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
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
	 * @return the investedBalance
	 */
	public Double getInvestedBalance() {
		return investedBalance;
	}

	/**
	 * @param investedBalance
	 *            the investedBalance to set
	 */
	public void setInvestedBalance(Double investedBalance) {
		this.investedBalance = investedBalance;
	}

	/**
	 * @return the currentBalance
	 */
	public Double getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance
	 *            the currentBalance to set
	 */
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the vatNumber
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param vatNumber
	 *            the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

}
