/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Set;

/**
 * @author Shivu
 *
 */
public class OrderSummaryResponse {
	private int ordersCount;
	private double ordersAmount;
	private int numberOfUsers;
	private Set<String> newUsers;
	private Set<String> oldUsers;

	/**
	 * @return the ordersCount
	 */
	public int getOrdersCount() {
		return ordersCount;
	}

	/**
	 * @param ordersCount
	 *            the ordersCount to set
	 */
	public void setOrdersCount(int ordersCount) {
		this.ordersCount = ordersCount;
	}

	/**
	 * @return the ordersAmount
	 */
	public double getOrdersAmount() {
		return ordersAmount;
	}

	/**
	 * @param ordersAmount
	 *            the ordersAmount to set
	 */
	public void setOrdersAmount(double ordersAmount) {
		this.ordersAmount = ordersAmount;
	}

	/**
	 * @return the numberOfUsers
	 */
	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	/**
	 * @param numberOfUsers
	 *            the numberOfUsers to set
	 */
	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	/**
	 * @return the newUsers
	 */
	public Set<String> getNewUsers() {
		return newUsers;
	}

	/**
	 * @param newUsers
	 *            the newUsers to set
	 */
	public void setNewUsers(Set<String> newUsers) {
		this.newUsers = newUsers;
	}

	/**
	 * @return the oldUsers
	 */
	public Set<String> getOldUsers() {
		return oldUsers;
	}

	/**
	 * @param oldUsers
	 *            the oldUsers to set
	 */
	public void setOldUsers(Set<String> oldUsers) {
		this.oldUsers = oldUsers;
	}
}
