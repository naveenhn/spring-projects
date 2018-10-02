/**
 * 
 */
package com.sarvah.mbg.batch.model;

/**
 * @author shivu
 *
 */
public class PriceInfo {

	private String dealerId;
	private double cost;
	private String shippingType;
	private double mrp;
	private double selling;
	private double savings;
	private double pctSavings;

	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}

	/**
	 * @param dealerId
	 *            the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @return the shippingType
	 */
	public String getShippingType() {
		return shippingType;
	}

	/**
	 * @param shippingType
	 *            the shippingType to set
	 */
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	/**
	 * @return the mrp
	 */
	public double getMrp() {
		return mrp;
	}

	/**
	 * @param mrp
	 *            the mrp to set
	 */
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the selling
	 */
	public double getSelling() {
		return selling;
	}

	/**
	 * @param selling
	 *            the selling to set
	 */
	public void setSelling(double selling) {
		this.selling = selling;
	}

	/**
	 * @return the savings
	 */
	public double getSavings() {
		return savings;
	}

	/**
	 * @param savings
	 *            the savings to set
	 */
	public void setSavings(double savings) {
		this.savings = savings;
	}

	/**
	 * @return the pctSavings
	 */
	public double getPctSavings() {
		return pctSavings;
	}

	/**
	 * @param pctSavings
	 *            the pctSavings to set
	 */
	public void setPctSavings(double pctSavings) {
		this.pctSavings = pctSavings;
	}
}
