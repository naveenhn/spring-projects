/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Set;

/**
 * @author Shivu
 *
 */
public class QuoteProductRequestParam {
	private String quoteProductId;
	private String productName;
	private String productId;
	private String brand;
	private String quantityType;
	private Integer quantity;
	private Set<SellerQuoteProductPricingRequestParam> productPricings;
	private String alertType;
	private Integer itemCount;
	private double totalQuoteAmount;

	/**
	 * @return the quoteProductId
	 */
	public String getQuoteProductId() {
		return quoteProductId;
	}

	/**
	 * @param quoteProductId
	 *            the quoteProductId to set
	 */
	public void setQuoteProductId(String quoteProductId) {
		this.quoteProductId = quoteProductId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the quantityType
	 */
	public String getQuantityType() {
		return quantityType;
	}

	/**
	 * @param quantityType
	 *            the quantityType to set
	 */
	public void setQuantityType(String quantityType) {
		this.quantityType = quantityType;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the productPricings
	 */
	public Set<SellerQuoteProductPricingRequestParam> getProductPricings() {
		return productPricings;
	}

	/**
	 * @param productPricings
	 *            the productPricings to set
	 */
	public void setProductPricings(
			Set<SellerQuoteProductPricingRequestParam> productPricings) {
		this.productPricings = productPricings;
	}

	/**
	 * @return the alertType
	 */
	public String getAlertType() {
		return alertType;
	}

	/**
	 * @param alertType
	 *            the alertType to set
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	/**
	 * @return the itemCount
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount
	 *            the itemCount to set
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the totalQuoteAmount
	 */
	public double getTotalQuoteAmount() {
		return totalQuoteAmount;
	}

	/**
	 * @param totalQuoteAmount
	 *            the totalQuoteAmount to set
	 */
	public void setTotalQuoteAmount(double totalQuoteAmount) {
		this.totalQuoteAmount = totalQuoteAmount;
	}
}
