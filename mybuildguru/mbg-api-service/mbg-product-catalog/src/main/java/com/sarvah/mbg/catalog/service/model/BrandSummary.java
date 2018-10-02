/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;

/**
 * @author Shivu
 *
 */
public class BrandSummary {
	private String brandId;
	private String brandName;

	public BrandSummary(ProductBrand productBrand) {
		if (productBrand != null) {
			this.brandId = productBrand.getId();
			this.brandName = productBrand.getName();
		}

	}

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
