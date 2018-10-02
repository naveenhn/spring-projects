/**
 * 
 */
package com.sarvah.mbg.rest.catalog.model;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductPrice;

/**
 * @author Shivu
 *
 */
public class ProductPricingUpdateRequestParam {
	private String productPricingId;
	private Set<ProductPrice> productPrices;
	private String note;

	/**
	 * @return the productPricingId
	 */
	public String getProductPricingId() {
		return productPricingId;
	}

	/**
	 * @param productPricingId
	 *            the productPricingId to set
	 */
	public void setProductPricingId(String productPricingId) {
		this.productPricingId = productPricingId;
	}

	/**
	 * @return the productPrices
	 */
	public Set<ProductPrice> getProductPrices() {
		return productPrices;
	}

	/**
	 * @param productPrices
	 *            the productPrices to set
	 */
	public void setProductPrices(Set<ProductPrice> productPrices) {
		this.productPrices = productPrices;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
