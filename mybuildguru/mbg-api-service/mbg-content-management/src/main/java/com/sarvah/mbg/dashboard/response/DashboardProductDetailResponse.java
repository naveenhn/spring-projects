/**
 * 
 */
package com.sarvah.mbg.dashboard.response;

import java.util.Set;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;

/**
 * @author Shivu
 *
 */
public class DashboardProductDetailResponse {
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
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * @return the subcategories
	 */
	public Set<SubCategory> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories
	 *            the subcategories to set
	 */
	public void setSubcategories(Set<SubCategory> subcategories) {
		this.subcategories = subcategories;
	}

	/**
	 * @return the features
	 */
	public ProductAttribute getFeatures() {
		return features;
	}

	/**
	 * @param features
	 *            the features to set
	 */
	public void setFeatures(ProductAttribute features) {
		this.features = features;
	}

	/**
	 * @return the attributes
	 */
	public Set<ProductAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Set<ProductAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the faqs
	 */
	public Set<ProductFaq> getFaqs() {
		return faqs;
	}

	/**
	 * @param faqs
	 *            the faqs to set
	 */
	public void setFaqs(Set<ProductFaq> faqs) {
		this.faqs = faqs;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the productTypes
	 */
	public Set<ProductType> getProductTypes() {
		return productTypes;
	}

	/**
	 * @param productTypes
	 *            the productTypes to set
	 */
	public void setProductTypes(Set<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

	/**
	 * @return the accessory
	 */
	public String getAccessory() {
		return accessory;
	}

	/**
	 * @param accessory
	 *            the accessory to set
	 */
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	/**
	 * @return the relatedProducts
	 */
	public Set<String> getRelatedProducts() {
		return relatedProducts;
	}

	/**
	 * @param relatedProducts
	 *            the relatedProducts to set
	 */
	public void setRelatedProducts(Set<String> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	/**
	 * @return the accessories
	 */
	public Set<String> getAccessories() {
		return accessories;
	}

	/**
	 * @param accessories
	 *            the accessories to set
	 */
	public void setAccessories(Set<String> accessories) {
		this.accessories = accessories;
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

	/**
	 * @return the tcDoc
	 */
	public File getTcDoc() {
		return tcDoc;
	}

	/**
	 * @param tcDoc
	 *            the tcDoc to set
	 */
	public void setTcDoc(File tcDoc) {
		this.tcDoc = tcDoc;
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
}
