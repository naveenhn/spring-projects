/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import java.util.Set;

/**
 * @author Shivu
 *
 */
public class PromotionInfoRequestParam {
	private String promoInfo;
	private String mobileNumbers;
	private Set<String> roleNames;

	/**
	 * @return the promoInfo
	 */
	public String getPromoInfo() {
		return promoInfo;
	}

	/**
	 * @param promoInfo
	 *            the promoInfo to set
	 */
	public void setPromoInfo(String promoInfo) {
		this.promoInfo = promoInfo;
	}

	/**
	 * @return the mobileNumbers
	 */
	public String getMobileNumbers() {
		return mobileNumbers;
	}

	/**
	 * @param mobileNumbers
	 *            the mobileNumbers to set
	 */
	public void setMobileNumbers(String mobileNumbers) {
		this.mobileNumbers = mobileNumbers;
	}

	/**
	 * @return the roleNames
	 */
	public Set<String> getRoleNames() {
		return roleNames;
	}

	/**
	 * @param roleNames
	 *            the roleNames to set
	 */
	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}
}
