/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

/**
 * @author Shivu
 *
 */
public class ContactInfoResponse {
	private String name;
	private String email;
	private String mobileNumber;
	private String zipcode;
	private String description;
	private String constructionType;
	private String asSoonRequired;
	private String serviceProviderId;

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
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber
	 *            the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(String serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
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
	 * @return the asSoonRequired
	 */
	public String getAsSoonRequired() {
		return asSoonRequired;
	}

	/**
	 * @param asSoonRequired
	 *            the asSoonRequired to set
	 */
	public void setAsSoonRequired(String asSoonRequired) {
		this.asSoonRequired = asSoonRequired;
	}
}
