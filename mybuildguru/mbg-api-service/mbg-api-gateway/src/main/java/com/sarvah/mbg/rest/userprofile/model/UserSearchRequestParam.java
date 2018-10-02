/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import javax.ws.rs.QueryParam;

import com.sarvah.mbg.rest.model.AbstractRequestParam;

/**
 * @author shivu s
 *
 */
public class UserSearchRequestParam extends AbstractRequestParam {

	@QueryParam("username")
	private String userName;

	@QueryParam("searchValue")
	private String searchValue;

	@QueryParam("fname")
	private String firstName;

	@QueryParam("lname")
	private String lastName;

	@QueryParam("email")
	private String email;

	@QueryParam("pnum")
	private String phoneNumber;

	@QueryParam("roleName")
	private String roleName;

	@QueryParam("status")
	private String status;

	@QueryParam("fullname")
	private String fullName;

	@QueryParam("area")
	private String area;

	@QueryParam("pack")
	private String pack;

	@QueryParam("lat")
	private String lat;

	@QueryParam("lon")
	private String lon;

	@QueryParam("skillSetName")
	private String skillSetName;

	@QueryParam("operatingCityName")
	private String operatingCityName;

	@QueryParam("subCategory")
	private String subCategory;

	@QueryParam("brand")
	private String brand;

	@QueryParam("productIds")
	private String productIds;

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
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * @param lon
	 *            the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the skillSetName
	 */
	public String getSkillSetName() {
		return skillSetName;
	}

	/**
	 * @param skillSetName
	 *            the skillSetName to set
	 */
	public void setSkillSetName(String skillSetName) {
		this.skillSetName = skillSetName;
	}

	/**
	 * @return the operatingCityName
	 */
	public String getOperatingCityName() {
		return operatingCityName;
	}

	/**
	 * @param operatingCityName
	 *            the operatingCityName to set
	 */
	public void setOperatingCityName(String operatingCityName) {
		this.operatingCityName = operatingCityName;
	}

	/**
	 * @return the subCategory
	 */
	public String getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the productIds
	 */
	public String getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds
	 *            the productIds to set
	 */
	public void setProductIds(String productIds) {
		this.productIds = productIds;
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
}
