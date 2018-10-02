package com.sarvah.mbg.catalog.service.impl;

/**
 * @author Shiva
 *
 */

/**
 * @author Shiva
 *
 */
public class SellerInformation {

	private String dealerId;

	private StringBuffer dealerName;

	private String prodId;

	private Double rating;

	private Double discountPercentage;

	private Double sellingPrice;

	private Double tax;

	private Double shippingCharge;

	private Integer minSLA;

	private Integer maxSLA;

	private Integer minQuantityBuy;

	// RequestQuote
	private String contactPersonName;
	private String mobile;
	private String email;

	/**
	 * @return the discountPercentage
	 */
	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage
	 *            the discountPercentage to set
	 */
	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

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
	 * @return the prodId
	 */
	public String getProdId() {
		return prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}

	/**
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the tax
	 */
	public Double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(Double tax) {
		this.tax = tax;
	}

	/**
	 * @return the shippingCharge
	 */
	public Double getShippingCharge() {
		return shippingCharge;
	}

	/**
	 * @param shippingCharge
	 *            the shippingCharge to set
	 */
	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	/**
	 * @return the minSLA
	 */
	public Integer getMinSLA() {
		return minSLA;
	}

	/**
	 * @param minSLA
	 *            the minSLA to set
	 */
	public void setMinSLA(Integer minSLA) {
		this.minSLA = minSLA;
	}

	/**
	 * @return the maxSLA
	 */
	public Integer getMaxSLA() {
		return maxSLA;
	}

	/**
	 * @param maxSLA
	 *            the maxSLA to set
	 */
	public void setMaxSLA(Integer maxSLA) {
		this.maxSLA = maxSLA;
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

	/**
	 * @return the dealerName
	 */
	public StringBuffer getDealerName() {
		return dealerName;
	}

	/**
	 * @param dealerName
	 *            the dealerName to set
	 */
	public void setDealerName(StringBuffer dealerName) {
		this.dealerName = dealerName;
	}

	/**
	 * @return the contactPersonName
	 */
	public String getContactPersonName() {
		return contactPersonName;
	}

	/**
	 * @param contactPersonName
	 *            the contactPersonName to set
	 */
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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

}
