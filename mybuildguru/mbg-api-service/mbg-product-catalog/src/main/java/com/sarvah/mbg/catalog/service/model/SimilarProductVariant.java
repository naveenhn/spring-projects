/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

/**
 * @author Shivu
 *
 */
public class SimilarProductVariant {
	private String variant;
	private String productId;

	/**
	 * @return the variant
	 */
	public String getVariant() {
		return variant;
	}

	/**
	 * @param variant
	 *            the variant to set
	 */
	public void setVariant(String variant) {
		this.variant = variant;
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
}
