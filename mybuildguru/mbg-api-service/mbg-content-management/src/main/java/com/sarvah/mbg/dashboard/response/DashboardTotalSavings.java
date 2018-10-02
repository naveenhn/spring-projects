/**
 * 
 */
package com.sarvah.mbg.dashboard.response;

/**
 * @author Shiva
 *
 */
public class DashboardTotalSavings {

	private int totalOrders;
	private double totalSavings;

	/**
	 * @return the totalOrders
	 */
	public int getTotalOrders() {
		return totalOrders;
	}

	/**
	 * @return the totalSavings
	 */
	public double getTotalSavings() {
		return totalSavings;
	}

	/**
	 * @param totalOrders
	 *            the totalOrders to set
	 */
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	/**
	 * @param totalSavings
	 *            the totalSavings to set
	 */
	public void setTotalSavings(double totalSavings) {
		this.totalSavings = totalSavings;
	}
}