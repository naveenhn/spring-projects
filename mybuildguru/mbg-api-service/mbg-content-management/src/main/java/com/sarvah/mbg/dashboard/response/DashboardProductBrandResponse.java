package com.sarvah.mbg.dashboard.response;

import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;

/**
 * 
 * @author Raju
 *
 */
public class DashboardProductBrandResponse {
	private ProductBrand brand;
	private Image brandImage;

	/**
	 * @return the brand
	 */
	public ProductBrand getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	/**
	 * @return the brandImage
	 */
	public Image getBrandImage() {
		return brandImage;
	}

	/**
	 * @param brandImage
	 *            the brandImage to set
	 */
	public void setBrandImage(Image brandImage) {
		this.brandImage = brandImage;
	}

}
