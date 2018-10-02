/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;

/**
 * @author naveen
 *
 */
@Document
public class User extends AbstractDocument {

	public enum Sex {
		MALE, FEMALE, OTHER;
	}

	//@Indexed(unique = true, dropDups = true)
	//@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "email is either blank or not in right format")
	private String username;

	
	private String userPhoneNumber;
	
	private String lastName;

	@NotNull
	private String firstName;

	@NotNull
	@TextIndexed
	private String fullName;

	private Description desc;

	private String title;

	private String sex;

	private Image profilePicture;

	@JsonIgnore
	private Set<Address> addresses;

	@JsonIgnore
	private Set<Role> roles;

	@NotNull
	private UserStatus status;

	/** Provider Specific **/
	private String contactName;

	private int numberOfProducts;

	private int numberOfDealers;

	/** service providers specific **/ 
	
	private Set<String> operatingCities;
	private Set<String> otherSkillSets;
	/** Dealer Specific **/
	private int numberOfPromotions;
	private int numberOfStores; // physical stores and not estore (per say
								// estore is always one)
	@JsonIgnore
	private String bankName;
	@JsonIgnore
	private String bankAccountNumber;
	@JsonIgnore
	private String ifscCode;

	@JsonIgnore
	private String panNumber;
	@JsonIgnore
	private String vatNumber;
	@JsonIgnore
	private String websiteUrl;

	private ConsolidatedRating rating;

	/** Business Associate Specific **/
	private Double investedBalance;

	private Double currentBalance;
	
	private Date activeSince;

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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the status
	 */
	public UserStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
	 * @return the addresses
	 */
	public Set<Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	/**
	 * 
	 * @param address
	 */
	public void addAddress(Address address) {
		if (this.addresses == null) {
			this.addresses = new HashSet<>();
		}
		address.setAddressId(addresses.size() + 1); // takes care of
													// incrementing address
		this.addresses.add(address);
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
		if (fullName != null) {
			this.fullName = fullName;
		} else {
			this.fullName = firstName != null ? (lastName != null ? firstName
					+ " " + lastName : firstName)
					: (lastName != null ? lastName : "");
		}

	}

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
	 * @return the numberOfProducts
	 */
	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	/**
	 * @param numberOfProducts
	 *            the numberOfProducts to set
	 */
	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	/**
	 * @return the numberOfDealers
	 */
	public int getNumberOfDealers() {
		return numberOfDealers;
	}

	/**
	 * @param numberOfDealers
	 *            the numberOfDealers to set
	 */
	public void setNumberOfDealers(int numberOfDealers) {
		this.numberOfDealers = numberOfDealers;
	}

	/**
	 * @return the numberOfPromotions
	 */
	public int getNumberOfPromotions() {
		return numberOfPromotions;
	}

	/**
	 * @param numberOfPromotions
	 *            the numberOfPromotions to set
	 */
	public void setNumberOfPromotions(int numberOfPromotions) {
		this.numberOfPromotions = numberOfPromotions;
	}

	/**
	 * @return the numberOfStores
	 */
	public int getNumberOfStores() {
		return numberOfStores;
	}

	/**
	 * @param numberOfStores
	 *            the numberOfStores to set
	 */
	public void setNumberOfStores(int numberOfStores) {
		this.numberOfStores = numberOfStores;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankAccountNumber
	 */
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	/**
	 * @param bankAccountNumber
	 *            the bankAccountNumber to set
	 */
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	/**
	 * @return the panNumber
	 */
	public String getPanNumber() {
		return panNumber;
	}

	/**
	 * @param panNumber
	 *            the panNumber to set
	 */
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
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

	/**
	 * @return the websiteUrl
	 */
	public String getWebsiteUrl() {
		return websiteUrl;
	}

	/**
	 * @param websiteUrl
	 *            the websiteUrl to set
	 */
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public void addRole(Role role) {
		if (roles == null) {
			roles = new HashSet<>();
		}
		roles.add(role);
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
	 * @param profilePicture the profilePicture to set
	 */
	public void setProfilePicture(Image profilePicture) {
		this.profilePicture = profilePicture;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(ConsolidatedRating rating) {
		this.rating = rating;
	}

	/**
	 * @return the rating
	 */
	public ConsolidatedRating getRating() {
		return rating;
	}

	/**
	 * @return the investedBalance
	 */
	public Double getInvestedBalance() {
		return investedBalance;
	}

	/**
	 * @param investedBalance the investedBalance to set
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
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the activeSince
	 */
	public Date getActiveSince() {
		return activeSince;
	}

	/**
	 * @param activeSince the activeSince to set
	 */
	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}

	/**
	 * @return the ifscCode
	 */
	public String getIfscCode() {
		return ifscCode;
	}

	/**
	 * @param ifscCode the ifscCode to set
	 */
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	/**
	 * @return the otherSkillSets
	 */
	public Set<String> getOtherSkillSets() {
		return otherSkillSets;
	}

	/**
	 * @param otherSkillSets the otherSkillSets to set
	 */
	public void setOtherSkillSets(Set<String> otherSkillSets) {
		this.otherSkillSets = otherSkillSets;
	}

	/**
	 * @return the userPhoneNumber
	 */
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	/**
	 * @param userPhoneNumber the userPhoneNumber to set
	 */
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	/**
	 * @return the operatingCities
	 */
	public Set<String> getOperatingCities() {
		return operatingCities;
	}

	/**
	 * @param operatingCities the operatingCities to set
	 */
	public void setOperatingCities(Set<String> operatingCities) {
		this.operatingCities = operatingCities;
	}
}
