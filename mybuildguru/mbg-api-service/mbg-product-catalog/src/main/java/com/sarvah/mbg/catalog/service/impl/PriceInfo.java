/**
 * 
 */
package com.sarvah.mbg.catalog.service.impl;

/**
 * @author Shiva
 *
 */
public class PriceInfo {

	private String dealerId;

	private double cost;

	private String shippingType;

	private double mrp;

	private double discountPercentage;

	private double tax;

	private double shippingCharge;

	private Integer minSla;

	private Integer maxSla;

	private Integer minQuantityBuy;

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
	 * @return the discountPercentage
	 */
	public double getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage
	 *            the discountPercentage to set
	 */
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the shippingCharge
	 */
	public double getShippingCharge() {
		return shippingCharge;
	}

	/**
	 * @param shippingCharge
	 *            the shippingCharge to set
	 */
	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	/**
	 * @return the minSla
	 */
	public Integer getMinSla() {
		return minSla;
	}

	/**
	 * @param minSla
	 *            the minSla to set
	 */
	public void setMinSla(Integer minSla) {
		this.minSla = minSla;
	}

	/**
	 * @return the maxSla
	 */
	public Integer getMaxSla() {
		return maxSla;
	}

	/**
	 * @param maxSla
	 *            the maxSla to set
	 */
	public void setMaxSla(Integer maxSla) {
		this.maxSla = maxSla;
	}

	/**
	 * @return the minQuantityBuy
	 */
	public Integer getMinQuantityBuy() {
		return minQuantityBuy;
	}

	/**
	 * @param minQuantityBuy
	 *            the minQuantityBuy to set
	 */
	public void setMinQuantityBuy(Integer minQuantityBuy) {
		this.minQuantityBuy = minQuantityBuy;
	}

}
