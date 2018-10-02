/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;

/**
 * @author shivu
 *
 */
public class ProductBrandResponse {
	private List<ProductBrand> brands;

	/**
	 * @return the brands
	 */
	public List<ProductBrand> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(List<ProductBrand> brands) {
		this.brands = brands;
	}
}
