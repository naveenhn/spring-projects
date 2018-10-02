/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductPrice;

/**
 * @author Shivu
 *
 */
public class ProductPricingSummaryView {
	private String productPricingId;
	private String productId;
	private String skuId;
	private String productName;
	private Set<String> category;
	private Set<String> subCategory;
	private String brand;
	private String productType;
	private Set<ProductPrice> productPrices;

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
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the category
	 */
	public Set<String> getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Set<String> category) {
		this.category = category;
	}

	/**
	 * @return the subCategory
	 */
	public Set<String> getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(Set<String> subCategory) {
		this.subCategory = subCategory;
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
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
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
}
