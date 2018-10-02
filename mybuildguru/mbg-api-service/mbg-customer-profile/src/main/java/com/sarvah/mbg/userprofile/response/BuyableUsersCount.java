/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

/**
 * @author Shivu
 *
 */
public class BuyableUsersCount {

	private int endusersCount;
	private int serviceProvidersCount;
	private int businessAssoicateCount;

	/**
	 * @return the endusersCount
	 */
	public int getEndusersCount() {
		return endusersCount;
	}

	/**
	 * @param endusersCount
	 *            the endusersCount to set
	 */
	public void setEndusersCount(int endusersCount) {
		this.endusersCount = endusersCount;
	}

	/**
	 * @return the serviceProvidersCount
	 */
	public int getServiceProvidersCount() {
		return serviceProvidersCount;
	}

	/**
	 * @param serviceProvidersCount
	 *            the serviceProvidersCount to set
	 */
	public void setServiceProvidersCount(int serviceProvidersCount) {
		this.serviceProvidersCount = serviceProvidersCount;
	}

	/**
	 * @return the businessAssoicateCount
	 */
	public int getBusinessAssoicateCount() {
		return businessAssoicateCount;
	}

	/**
	 * @param businessAssoicateCount
	 *            the businessAssoicateCount to set
	 */
	public void setBusinessAssoicateCount(int businessAssoicateCount) {
		this.businessAssoicateCount = businessAssoicateCount;
	}
}