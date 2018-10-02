/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Set;

/**
 * @author Shivu
 *
 */
public class QuoteProductResponse {
	private String quoteProductId;
	private String productName;
	private String productId;
	private String brand;
	private String quantityType;
	private Integer quantity;
	private Set<QuoteProductPricingResponse> productPricings;

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
	public Set<QuoteProductPricingResponse> getProductPricings() {
		return productPricings;
	}

	/**
	 * @param productPricings
	 *            the productPricings to set
	 */
	public void setProductPricings(
			Set<QuoteProductPricingResponse> productPricings) {
		this.productPricings = productPricings;
	}
}
