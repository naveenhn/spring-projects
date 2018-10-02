package com.sarvah.mbg.catalog.service.model;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

public class DealerOnboardedProductDetails {
	private String id;
	private String name;
	private String model;
	private Set<Category> mainCategory;
	private Set<SubCategory> subCategory;
	private ProductStatus status;
	private ProductAsset assets;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Set<Category> getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(Set<Category> mainCategory) {
		this.mainCategory = mainCategory;
	}

	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

	/**
	 * @return the assets
	 */
	public ProductAsset getAssets() {
		return assets;
	}

	/**
	 * @param assets
	 *            the assets to set
	 */
	public void setAssets(ProductAsset assets) {
		this.assets = assets;
	}

}
