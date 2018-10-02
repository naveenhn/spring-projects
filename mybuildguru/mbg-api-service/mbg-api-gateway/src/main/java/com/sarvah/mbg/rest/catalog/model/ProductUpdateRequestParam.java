package com.sarvah.mbg.rest.catalog.model;

import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;

public class ProductUpdateRequestParam {

	private String name;
	private String longName;
	private String desc;
	private Set<String> subcategories;
	private Set<String> productTypes;
	private String productAssets;
	private Set<ProductAttribute> productAttributes;
	private String price;
	private String accessory;
	private List<String> relatedProducts;
	private List<String> accessories;
	private String status;
	private String brandName;
	private String quantityType;
	private String key;
	private String isMultivalued;
	private Set<String> values;
	private String model;
	private Set<ProductFaq> faqs;
	// SellerQuoteProduct update purpose
	private QuoteProductRequestParam product;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName
	 *            the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the subcategories
	 */
	public Set<String> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories
	 *            the subcategories to set
	 */
	public void setSubcategories(Set<String> subcategories) {
		this.subcategories = subcategories;
	}

	/**
	 * @return the productTypes
	 */
	public Set<String> getProductTypes() {
		return productTypes;
	}

	/**
	 * @param productTypes
	 *            the productTypes to set
	 */
	public void setProductTypes(Set<String> productTypes) {
		this.productTypes = productTypes;
	}

	/**
	 * @return the productAssets
	 */
	public String getProductAssets() {
		return productAssets;
	}

	/**
	 * @param productAssets
	 *            the productAssets to set
	 */
	public void setProductAssets(String productAssets) {
		this.productAssets = productAssets;
	}

	/**
	 * @return the productAttributes
	 */
	public Set<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	/**
	 * @param productAttributes
	 *            the productAttributes to set
	 */
	public void setProductAttributes(Set<ProductAttribute> productAttributes) {
		this.productAttributes = productAttributes;
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
	public List<String> getRelatedProducts() {
		return relatedProducts;
	}

	/**
	 * @param relatedProducts
	 *            the relatedProducts to set
	 */
	public void setRelatedProducts(List<String> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	/**
	 * @return the accessories
	 */
	public List<String> getAccessories() {
		return accessories;
	}

	/**
	 * @param accessories
	 *            the accessories to set
	 */
	public void setAccessories(List<String> accessories) {
		this.accessories = accessories;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the quantityType
	 */
	public String getQuantityType() {
		return quantityType;
	}

	/**
	 * @param quantityType
	 *            the quantityType to set
	 */
	public void setQuantityType(String quantityType) {
		this.quantityType = quantityType;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the isMultivalued
	 */
	public String getIsMultivalued() {
		return isMultivalued;
	}

	/**
	 * @param isMultivalued
	 *            the isMultivalued to set
	 */
	public void setIsMultivalued(String isMultivalued) {
		this.isMultivalued = isMultivalued;
	}

	/**
	 * @return the values
	 */
	public Set<String> getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Set<String> values) {
		this.values = values;
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
	 * @return the product
	 */
	public QuoteProductRequestParam getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(QuoteProductRequestParam product) {
		this.product = product;
	}
}
