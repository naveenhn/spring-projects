package com.sarvah.mbg.catalog.service.model;

import java.util.Set;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;

public class ProductDetailsResponse {
	private String productId;
	private String productName;
	private String model;
	private String skuId;
	private String brand;
	private Set<SubCategory> subcategories;
	private ProductAttribute features;
	private Set<ProductAttribute> attributes;
	private Set<ProductFaq> faqs;
	private String price;
	private Set<ProductType> productTypes;
	private String accessory;
	private Set<String> relatedProducts;
	private Set<String> accessories;
	private ProductAsset assets;
	private File tcDoc;
	private ConsolidatedRating rating;
	private ProductQuantityType productQuantityType;

	/**
	 * @return the productQuantityType
	 */
	public ProductQuantityType getProductQuantityType() {
		return productQuantityType;
	}

	/**
	 * @param productQuantityType
	 *            the productQuantityType to set
	 */
	public void setProductQuantityType(ProductQuantityType productQuantityType) {
		this.productQuantityType = productQuantityType;
	}

	/**
	 * @return the rating
	 */
	public ConsolidatedRating getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(ConsolidatedRating rating) {
		this.rating = rating;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductAsset getAssets() {
		return assets;
	}

	public void setAssets(ProductAsset assets) {
		this.assets = assets;
	}

	public File getTcDoc() {
		return tcDoc;
	}

	public void setTcDoc(File tcDoc) {
		this.tcDoc = tcDoc;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Set<SubCategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(Set<SubCategory> subcategories) {
		this.subcategories = subcategories;
	}

	public ProductAttribute getFeatures() {
		return features;
	}

	public void setFeatures(ProductAttribute features) {
		this.features = features;
	}

	public Set<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

	public Set<ProductFaq> getFaqs() {
		return faqs;
	}

	public void setFaqs(Set<ProductFaq> faqs) {
		this.faqs = faqs;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Set<ProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(Set<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

	public Set<String> getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(Set<String> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	public Set<String> getAccessories() {
		return accessories;
	}

	public void setAccessories(Set<String> accessories) {
		this.accessories = accessories;
	}
}
