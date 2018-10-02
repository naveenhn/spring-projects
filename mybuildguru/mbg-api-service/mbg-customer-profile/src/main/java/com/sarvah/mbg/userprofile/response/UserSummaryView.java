package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;

public class UserSummaryView {

	private String userId;
	private String name;
	private Date createdDate;
	private String emailId;
	private String panNumber;
	private String packageName;
	private long totalProducts;
	private UserStatus status;
	private boolean isVerified;

	// provider
	private List<String> brandName;
	private long totalDealers;

	// dealer
	private List<String> shopname;
	private int estimatedDeliveryDate;
	private int totalShops;
	private String vatNumber;

	// enduser
	private String firstName;
	private String lastName;
	private String fullName;
	private String email;
	private String mobile;
	private Address address;
	private long totalPurchasedOrders; // For both enduser and Business //
										// Associate
	private long totalshortlistedProducts;
	private List<ProjectType> projectType;
	private String constructionType;
	private List<Project> postedProjects;
	private Integer noOfPostedProjects;

	// Business Associates
	private Double investedBalance; // For Business Associate
	private Double currentBalance; // For Business Associate

	// Architect or Interior
	private long totalOffices;
	private long bidsCount;

	// Service providers

	private String role;
	private String phoneNumber;

	private String customerCode;

	private int lastOrderDays;

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
	 * @return the totalProducts
	 */
	public long getTotalProducts() {
		return totalProducts;
	}

	/**
	 * @param totalProducts
	 *            the totalProducts to set
	 */
	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
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
	public long getTotalDealers() {
		return totalDealers;
	}

	/**
	 * @param totalDealers
	 *            the totalDealers to set
	 */
	public void setTotalDealers(long totalDealers) {
		this.totalDealers = totalDealers;
	}

	/**
	 * @return the shopname
	 */
	public List<String> getShopname() {
		return shopname;
	}

	/**
	 * @param shopname
	 *            the shopname to set
	 */
	public void setShopname(List<String> shopname) {
		this.shopname = shopname;
	}

	/**
	 * @return the estimatedDeliveryDate
	 */
	public int getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	/**
	 * @param estimatedDeliveryDate
	 *            the estimatedDeliveryDate to set
	 */
	public void setEstimatedDeliveryDate(int estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	/**
	 * @return the totalShops
	 */
	public int getTotalShops() {
		return totalShops;
	}

	/**
	 * @param totalShops
	 *            the totalShops to set
	 */
	public void setTotalShops(int totalShops) {
		this.totalShops = totalShops;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	 * @return the totalPurchasedOrders
	 */
	public long getTotalPurchasedOrders() {
		return totalPurchasedOrders;
	}

	/**
	 * @param totalPurchasedOrders
	 *            the totalPurchasedOrders to set
	 */
	public void setTotalPurchasedOrders(long totalPurchasedOrders) {
		this.totalPurchasedOrders = totalPurchasedOrders;
	}

	/**
	 * @return the totalshortlistedProducts
	 */
	public long getTotalshortlistedProducts() {
		return totalshortlistedProducts;
	}

	/**
	 * @param totalshortlistedProducts
	 *            the totalshortlistedProducts to set
	 */
	public void setTotalshortlistedProducts(long totalshortlistedProducts) {
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
	 * @return the constructionType
	 */
	public String getConstructionType() {
		return constructionType;
	}

	/**
	 * @param constructionType
	 *            the constructionType to set
	 */
	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}

	/**
	 * @return the postedProjects
	 */
	public List<Project> getPostedProjects() {
		return postedProjects;
	}

	/**
	 * @param postedProjects
	 *            the postedProjects to set
	 */
	public void setPostedProjects(List<Project> postedProjects) {
		this.postedProjects = postedProjects;
	}

	/**
	 * @return the noOfPostedProjects
	 */
	public Integer getNoOfPostedProjects() {
		return noOfPostedProjects;
	}

	/**
	 * @param noOfPostedProjects
	 *            the noOfPostedProjects to set
	 */
	public void setNoOfPostedProjects(Integer noOfPostedProjects) {
		this.noOfPostedProjects = noOfPostedProjects;
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
	 * @return the totalOffices
	 */
	public long getTotalOffices() {
		return totalOffices;
	}

	/**
	 * @param totalOffices
	 *            the totalOffices to set
	 */
	public void setTotalOffices(long totalOffices) {
		this.totalOffices = totalOffices;
	}

	/**
	 * @return the bidsCount
	 */
	public long getBidsCount() {
		return bidsCount;
	}

	/**
	 * @param bidsCount
	 *            the bidsCount to set
	 */
	public void setBidsCount(long bidsCount) {
		this.bidsCount = bidsCount;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
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

}
