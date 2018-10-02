/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

/**
 * @author Shivu
 *
 */
public class ProfileCompletenessResponse {
	private int registration;
	private int contactDetails;
	private int profileDescription;
	private int profileImage;
	private int portfolios;
	private int services;
	private int cities;

	/**
	 * @return the registration
	 */
	public int getRegistration() {
		return registration;
	}

	/**
	 * @param registration
	 *            the registration to set
	 */
	public void setRegistration(int registration) {
		this.registration = registration;
	}

	/**
	 * @return the contactDetails
	 */
	public int getContactDetails() {
		return contactDetails;
	}

	/**
	 * @param contactDetails
	 *            the contactDetails to set
	 */
	public void setContactDetails(int contactDetails) {
		this.contactDetails = contactDetails;
	}

	/**
	 * @return the profileDescription
	 */
	public int getProfileDescription() {
		return profileDescription;
	}

	/**
	 * @param profileDescription
	 *            the profileDescription to set
	 */
	public void setProfileDescription(int profileDescription) {
		this.profileDescription = profileDescription;
	}

	/**
	 * @return the profileImage
	 */
	public int getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage
	 *            the profileImage to set
	 */
	public void setProfileImage(int profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the portfolios
	 */
	public int getPortfolios() {
		return portfolios;
	}

	/**
	 * @param portfolios
	 *            the portfolios to set
	 */
	public void setPortfolios(int portfolios) {
		this.portfolios = portfolios;
	}

	/**
	 * @return the services
	 */
	public int getServices() {
		return services;
	}

	/**
	 * @param services
	 *            the services to set
	 */
	public void setServices(int services) {
		this.services = services;
	}

	/**
	 * @return the cities
	 */
	public int getCities() {
		return cities;
	}

	/**
	 * @param cities
	 *            the cities to set
	 */
	public void setCities(int cities) {
		this.cities = cities;
	}
}
