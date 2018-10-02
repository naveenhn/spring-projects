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
public class ProductPricingCreateRequestParam {
	private String productId;
	private Set<ProductPrice> productPrices;
	private String note;

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
