/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author naveen
 *
 */
@Document
public class QuoteRequestProduct extends AbstractDocument {
	@NotNull
	private String productName;
	private String productId;
	private String brand;
	private String quantityType;
	private Integer quantity;

	@DBRef
	private Set<SellerQuoteProductPricing> quoteProductPricings;

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
	 * @return the quoteProductPricings
	 */
	public Set<SellerQuoteProductPricing> getQuoteProductPricings() {
		return quoteProductPricings;
	}

	/**
	 * @param quoteProductPricings
	 *            the quoteProductPricings to set
	 */
	public void setQuoteProductPricings(
			Set<SellerQuoteProductPricing> quoteProductPricings) {
		this.quoteProductPricings = quoteProductPricings;
	}
}
