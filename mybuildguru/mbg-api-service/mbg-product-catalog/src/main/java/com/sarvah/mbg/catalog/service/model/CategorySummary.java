/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

import com.sarvah.mbg.domain.mongo.catalog.Category;

/**
 * @author shivu
 *
 */
public class CategorySummary {

	private String catId;
	private String catName;

	public CategorySummary(Category category) {
		if (category != null) {
			this.catId = category.getId();
			this.catName = category.getName();
		}
	}

	private List<SubCategorySummary> subCategories;
	
	private boolean isCategoryHaveProducts;

	/**
	 * @return the catId
	 */
	public String getCatId() {
		return catId;
	}

	/**
	 * @param catId
	 *            the catId to set
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}

	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}

	/**
	 * @param catName
	 *            the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	/**
	 * @return the subCategories
	 */
	public List<SubCategorySummary> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories
	 *            the subCategories to set
	 */
	public void setSubCategories(List<SubCategorySummary> subCategories) {
		this.subCategories = subCategories;
	}

	/**
	 * @return the isCategoryHaveProducts
	 */
	public boolean isCategoryHaveProducts() {
		return isCategoryHaveProducts;
	}

	/**
	 * @param isCategoryHaveProducts the isCategoryHaveProducts to set
	 */
	public void setCategoryHaveProducts(boolean isCategoryHaveProducts) {
		this.isCategoryHaveProducts = isCategoryHaveProducts;
	}

}
