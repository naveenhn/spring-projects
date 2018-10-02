/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author Shiva
 *
 */
public class SearchFilters {

	private Collection<ProductBrand> brands;

	private Collection<ProductType> types;

	private Collection<ProductAttribute> attributes;

	private Collection<SubCategory> subCategories;

	private double minPrice;

	private double maxPrice;

	private Map<String, Long> subCatNameAndProductCountMap;

	/**
	 * @return the minPrice
	 */
	public double getMinPrice() {
		return minPrice;
	}

	/**
	 * @param minPrice
	 *            the minPrice to set
	 */
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * @return the maxPrice
	 */
	public double getMaxPrice() {
		return maxPrice;
	}

	/**
	 * @param maxPrice
	 *            the maxPrice to set
	 */
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Set<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the attributes
	 */
	public Collection<ProductAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Collection<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the types
	 */
	public Collection<ProductType> getTypes() {
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(Collection<ProductType> types) {
		this.types = types;
	}

	/**
	 * @return the brands
	 */
	public Collection<ProductBrand> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(Collection<ProductBrand> brands) {
		this.brands = brands;
	}

	/**
	 * @return the subCategories
	 */
	public Collection<SubCategory> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories
	 *            the subCategories to set
	 */
	public void setSubCategories(Collection<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

	/**
	 * @return the subCatNameAndProductCountMap
	 */
	public Map<String, Long> getSubCatNameAndProductCountMap() {
		return subCatNameAndProductCountMap;
	}

	/**
	 * @param subCatNameAndProductCountMap
	 *            the subCatNameAndProductCountMap to set
	 */
	public void setSubCatNameAndProductCountMap(
			Map<String, Long> subCatNameAndProductCountMap) {
		this.subCatNameAndProductCountMap = subCatNameAndProductCountMap;
	}
}
