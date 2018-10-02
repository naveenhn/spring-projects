/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductCustomSearch.
 *
 * @author Naveen
 */
public class ProductCustomSearch {

	/** The sub category names. */
	private List<String> subCategoryNames;

	/** The product type names. */
	private List<String> productTypeNames;

	/** The min price. */
	private Double minPrice;

	/** The max price. */
	private Double maxPrice;

	/** The min rating. */
	private Double minRating;

	/** The max rating. */
	private Double maxRating;

	/** The category names. */
	private List<String> categories;

	/** The product brands. */
	private List<String> productBrandIds;

	/** The product statuses. */
	private List<ProductStatus> productStatuses;

	/** The attribute search map. */
	private Map<String, List<String>> attributeSearchMap;

	/** The search text. */
	private String searchText;

	/** The product ids. */
	private Set<String> productIds;

	/** The required fields. */
	private Set<String> requiredFields;

	private int limit;

	/**
	 * Gets the sub category names.
	 *
	 * @return the subCategoryNames
	 */
	public List<String> getSubCategoryNames() {
		return subCategoryNames;
	}

	/**
	 * Sets the sub category names.
	 *
	 * @param subCategoryNames
	 *            the subCategoryNames to set
	 */
	public void setSubCategoryNames(List<String> subCategoryNames) {
		this.subCategoryNames = subCategoryNames;
	}

	/**
	 * Gets the product type names.
	 *
	 * @return the productTypeNames
	 */
	public List<String> getProductTypeNames() {
		return productTypeNames;
	}

	/**
	 * Sets the product type names.
	 *
	 * @param productTypeNames
	 *            the productTypeNames to set
	 */
	public void setProductTypeNames(List<String> productTypeNames) {
		this.productTypeNames = productTypeNames;
	}

	/**
	 * Gets the min price.
	 *
	 * @return the minPrice
	 */
	public Double getMinPrice() {
		return minPrice;
	}

	/**
	 * Sets the min price.
	 *
	 * @param minPrice
	 *            the minPrice to set
	 */
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * Gets the max price.
	 *
	 * @return the maxPrice
	 */
	public Double getMaxPrice() {
		return maxPrice;
	}

	/**
	 * Sets the max price.
	 *
	 * @param maxPrice
	 *            the maxPrice to set
	 */
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * Gets the min rating.
	 *
	 * @return the minRating
	 */
	public Double getMinRating() {
		return minRating;
	}

	/**
	 * Sets the min rating.
	 *
	 * @param minRating
	 *            the minRating to set
	 */
	public void setMinRating(Double minRating) {
		this.minRating = minRating;
	}

	/**
	 * Gets the max rating.
	 *
	 * @return the maxRating
	 */
	public Double getMaxRating() {
		return maxRating;
	}

	/**
	 * Sets the max rating.
	 *
	 * @param maxRating
	 *            the maxRating to set
	 */
	public void setMaxRating(Double maxRating) {
		this.maxRating = maxRating;
	}

	/**
	 * Gets the product statuses.
	 *
	 * @return the productStatuses
	 */
	public List<ProductStatus> getProductStatuses() {
		return productStatuses;
	}

	/**
	 * Sets the product statuses.
	 *
	 * @param productStatuses
	 *            the productStatuses to set
	 */
	public void setProductStatuses(List<ProductStatus> productStatuses) {
		this.productStatuses = productStatuses;
	}

	/**
	 * Gets the search text.
	 *
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * Sets the search text.
	 *
	 * @param searchText
	 *            the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	/**
	 * Gets the product brand ids.
	 *
	 * @return the productBrandIds
	 */
	public List<String> getProductBrandIds() {
		return productBrandIds;
	}

	/**
	 * Sets the product brand ids.
	 *
	 * @param productBrandIds
	 *            the productBrandIds to set
	 */
	public void setProductBrandIds(List<String> productBrandIds) {
		this.productBrandIds = productBrandIds;
	}

	/**
	 * Gets the attribute search map.
	 *
	 * @return the attributeSearchMap
	 */
	public Map<String, List<String>> getAttributeSearchMap() {
		return attributeSearchMap;
	}

	/**
	 * Sets the attribute search map.
	 *
	 * @param attributeSearchMap
	 *            the attributeSearchMap to set
	 */
	public void setAttributeSearchMap(
			Map<String, List<String>> attributeSearchMap) {
		this.attributeSearchMap = attributeSearchMap;
	}

	/**
	 * Gets the product ids.
	 *
	 * @return the productIds
	 */
	public Set<String> getProductIds() {
		return productIds;
	}

	/**
	 * Sets the product ids.
	 *
	 * @param productIds
	 *            the productIds to set
	 */
	public void setProductIds(Set<String> productIds) {
		this.productIds = productIds;
	}

	/**
	 * Gets the required fields.
	 *
	 * @return the requiredFields
	 */
	public Set<String> getRequiredFields() {
		return requiredFields;
	}

	/**
	 * Sets the required fields.
	 *
	 * @param requiredFields
	 *            the requiredFields to set
	 */
	public void setRequiredFields(Set<String> requiredFields) {
		this.requiredFields = requiredFields;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
