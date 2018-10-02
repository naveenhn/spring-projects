/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author shivu
 *
 */
public class SubCategorySummary {

	private String subCatId;
	private String subCatname;

	public SubCategorySummary(SubCategory subCategory) {
		if (subCategory != null) {
			this.subCatId = subCategory.getId();
			this.subCatname = subCategory.getName();
		}

	}

	private List<ProductTypeSummary> productTypes;

	private List<BrandSummary> productBrands;

	private boolean isSubcategoryHaveProducts;

	/**
	 * @return the subCatId
	 */
	public String getSubCatId() {
		return subCatId;
	}

	/**
	 * @param subCatId
	 *            the subCatId to set
	 */
	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}

	/**
	 * @return the subCatname
	 */
	public String getSubCatname() {
		return subCatname;
	}

	/**
	 * @param subCatname
	 *            the subCatname to set
	 */
	public void setSubCatname(String subCatname) {
		this.subCatname = subCatname;
	}

	/**
	 * @return the productTypes
	 */
	public List<ProductTypeSummary> getProductTypes() {
		return productTypes;
	}

	/**
	 * @param productTypes
	 *            the productTypes to set
	 */
	public void setProductTypes(List<ProductTypeSummary> productTypes) {
		this.productTypes = productTypes;
	}

	/**
	 * @return the isSubcategoryHaveProducts
	 */
	public boolean isSubcategoryHaveProducts() {
		return isSubcategoryHaveProducts;
	}

	/**
	 * @param isSubcategoryHaveProducts
	 *            the isSubcategoryHaveProducts to set
	 */
	public void setSubcategoryHaveProducts(boolean isSubcategoryHaveProducts) {
		this.isSubcategoryHaveProducts = isSubcategoryHaveProducts;
	}

	/**
	 * @return the productBrands
	 */
	public List<BrandSummary> getProductBrands() {
		return productBrands;
	}

	/**
	 * @param productBrands
	 *            the productBrands to set
	 */
	public void setProductBrands(List<BrandSummary> productBrands) {
		this.productBrands = productBrands;
	}

}
