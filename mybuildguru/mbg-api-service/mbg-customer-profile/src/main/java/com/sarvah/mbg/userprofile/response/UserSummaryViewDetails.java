package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;

public class UserSummaryViewDetails {
	private String userId;
	private String firstName;
	private String lastName;
	private String fullName;
	private String contactName;
	private Date createdDate;
	private Date activeSince;
	private String emailId;
	private String phoneNumber;
	private String packageName;
	private Integer totalOffices;
	private Long bidsCount;
	private Set<Address> addresses;
	private Double rating;
	private String bankName;
	private String bankAccountNumber;
	private String panNumber;
	private String vatNumber;
	private String websiteUrl;
	private List<Image> Portfolio;
	private Set<String> features;
	private String desc;
	private boolean isVerified;
	// dealer
	private Long totalOnboardedProducts;
	private Integer totalShops;
	private Integer estimatedDeliveryDate;
	private List<String> shopName;
	private Integer totalProducts;
	private Double ratingVal;
	private UserStatus status;

	// provider
	private List<String> brandName;
	private Integer totalDealers;

	// endUser
	private Integer totalshortlistedProducts;
	private Integer totalPurchasedOrders;
	private List<ProjectType> projectType;
	private List<Project> projectList;

	// business Associate
	private Double investedBalance;
	private Double currentBalance;

	// ServiceProviders
	private Image photo;
	private String roleName;
	private String title;

	// customerCode
	private String customerCode;

	// dealer or Business Associate
	private String ifscCode;

	// Service providers
	private Set<String> otherSkillSets;
	private Set<String> operatingCities;

	/**
	 * @return the ifscCode
	 */
	public String getIfscCode() {
		return ifscCode;
	}

	/**
	 * @param ifscCode
	 *            the ifscCode to set
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
	 * @param otherSkillSets
	 *            the otherSkillSets to set
	 */
	public void setOtherSkillSets(Set<String> otherSkillSets) {
		this.otherSkillSets = otherSkillSets;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * @return the totalOffices
	 */
	public Integer getTotalOffices() {
		return totalOffices;
	}

	/**
	 * @param totalOffices
	 *            the totalOffices to set
	 */
	public void setTotalOffices(Integer totalOffices) {
		this.totalOffices = totalOffices;
	}

	/**
	 * @return the bidsCount
	 */
	public Long getBidsCount() {
		return bidsCount;
	}

	/**
	 * @param bidsCount
	 *            the bidsCount to set
	 */
	public void setBidsCount(Long bidsCount) {
		this.bidsCount = bidsCount;
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
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
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

	/**
	 * @return the portfolio
	 */
	public List<Image> getPortfolio() {
		return Portfolio;
	}

	/**
	 * @param portfolio
	 *            the portfolio to set
	 */
	public void setPortfolio(List<Image> portfolio) {
		Portfolio = portfolio;
	}

	/**
	 * @return the features
	 */
	public Set<String> getFeatures() {
		return features;
	}

	/**
	 * @param features
	 *            the features to set
	 */
	public void setFeatures(Set<String> features) {
		this.features = features;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the totalOnboardedProducts
	 */
	public Long getTotalOnboardedProducts() {
		return totalOnboardedProducts;
	}

	/**
	 * @param totalOnboardedProducts
	 *            the totalOnboardedProducts to set
	 */
	public void setTotalOnboardedProducts(Long totalOnboardedProducts) {
		this.totalOnboardedProducts = totalOnboardedProducts;
	}

	/**
	 * @return the totalShops
	 */
	public Integer getTotalShops() {
		return totalShops;
	}

	/**
	 * @param totalShops
	 *            the totalShops to set
	 */
	public void setTotalShops(Integer totalShops) {
		this.totalShops = totalShops;
	}

	/**
	 * @return the estimatedDeliveryDate
	 */
	public Integer getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	/**
	 * @param estimatedDeliveryDate
	 *            the estimatedDeliveryDate to set
	 */
	public void setEstimatedDeliveryDate(Integer estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	/**
	 * @return the shopName
	 */
	public List<String> getShopName() {
		return shopName;
	}

	/**
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(List<String> shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the totalProducts
	 */
	public Integer getTotalProducts() {
		return totalProducts;
	}

	/**
	 * @param totalProducts
	 *            the totalProducts to set
	 */
	public void setTotalProducts(Integer totalProducts) {
		this.totalProducts = totalProducts;
	}

	/**
	 * @return the ratingVal
	 */
	public Double getRatingVal() {
		return ratingVal;
	}

	/**
	 * @param ratingVal
	 *            the ratingVal to set
	 */
	public void setRatingVal(Double ratingVal) {
		this.ratingVal = ratingVal;
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
	 * @return the brandName
	 */
	public List<String> getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(List<String> brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the totalDealers
	 */
	public Integer getTotalDealers() {
		return totalDealers;
	}

	/**
	 * @param totalDealers
	 *            the totalDealers to set
	 */
	public void setTotalDealers(Integer totalDealers) {
		this.totalDealers = totalDealers;
	}

	/**
	 * @return the totalshortlistedProducts
	 */
	public Integer getTotalshortlistedProducts() {
		return totalshortlistedProducts;
	}

	/**
	 * @param totalshortlistedProducts
	 *            the totalshortlistedProducts to set
	 */
	public void setTotalshortlistedProducts(Integer totalshortlistedProducts) {
		this.totalshortlistedProducts = totalshortlistedProducts;
	}

	/**
	 * @return the projectType
	 */
	public List<ProjectType> getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(List<ProjectType> projectType) {
		this.projectType = projectType;
	}

	/**
	 * @return the projectList
	 */
	public List<Project> getProjectList() {
		return projectList;
	}

	/**
	 * @param projectList
	 *            the projectList to set
	 */
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
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
	 * @return the photo
	 */
	public Image getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(Image photo) {
		this.photo = photo;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 * @return the totalPurchasedOrders
	 */
	public Integer getTotalPurchasedOrders() {
		return totalPurchasedOrders;
	}

	/**
	 * @param totalPurchasedOrders
	 *            the totalPurchasedOrders to set
	 */
	public void setTotalPurchasedOrders(Integer totalPurchasedOrders) {
		this.totalPurchasedOrders = totalPurchasedOrders;
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
}
