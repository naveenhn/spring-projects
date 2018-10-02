package com.sarvah.mbg.rest.userprofile.model;

import javax.ws.rs.QueryParam;

public class ProjectGetRequestParam {
	@QueryParam("pname")
	private String pname;

	@QueryParam("budget")
	private String budget;

	@QueryParam("constructionType")
	private String constructionType;

	@QueryParam("projectType")
	private String projectType;

	@QueryParam("status")
	private String status;

	@QueryParam("addressLine1")
	private String addressLine1;

	@QueryParam("addressLine2")
	private String addressLine2;

	@QueryParam("city")
	private String city;

	@QueryParam("state")
	private String state;

	@QueryParam("country")
	private String country;

	@QueryParam("phoneType")
	private String phoneType;

	@QueryParam("zipcode")
	private String zipcode;

	@QueryParam("email")
	private String email;

	@QueryParam("lat")
	private String latname;

	@QueryParam("long")
	private String longname;

	/**
	 * @return the latname
	 */
	public String getLatname() {
		return latname;
	}

	/**
	 * @param latname
	 *            the latname to set
	 */
	public void setLatname(String latname) {
		this.latname = latname;
	}

	/**
	 * @return the longname
	 */
	public String getLongname() {
		return longname;
	}

	/**
	 * @param longname
	 *            the longname to set
	 */
	public void setLongname(String longname) {
		this.longname = longname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
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
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the phoneType
	 */
	public String getPhoneType() {
		return phoneType;
	}

	/**
	 * @param phoneType
	 *            the phoneType to set
	 */
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
