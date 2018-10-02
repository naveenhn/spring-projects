/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import com.sarvah.mbg.domain.mongo.catalog.ProductType;

/**
 * @author shivu
 *
 */
public class ProductTypeSummary {
	private String prodTypeId;
	private String prodTypeName;

	public ProductTypeSummary(ProductType productType) {
		if (productType != null) {
			this.prodTypeId = productType.getId();
			this.prodTypeName = productType.getName();
		}

	}

	private boolean isProductTypeHaveProducts;

	/**
	 * @return the prodTypeId
	 */
	public String getProdTypeId() {
		return prodTypeId;
	}

	/**
	 * @param prodTypeId
	 *            the prodTypeId to set
	 */
	public void setProdTypeId(String prodTypeId) {
		this.prodTypeId = prodTypeId;
	}

	/**
	 * @return the prodTypeName
	 */
	public String getProdTypeName() {
		return prodTypeName;
	}

	/**
	 * @param prodTypeName
	 *            the prodTypeName to set
	 */
	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}

	/**
	 * @return the isProductTypeHaveProducts
	 */
	public boolean isProductTypeHaveProducts() {
		return isProductTypeHaveProducts;
	}

	/**
	 * @param isProductTypeHaveProducts
	 *            the isProductTypeHaveProducts to set
	 */
	public void setProductTypeHaveProducts(boolean isProductTypeHaveProducts) {
		this.isProductTypeHaveProducts = isProductTypeHaveProducts;
	}

}
