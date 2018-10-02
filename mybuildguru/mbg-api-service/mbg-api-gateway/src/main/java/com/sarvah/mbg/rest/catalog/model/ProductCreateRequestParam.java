package com.sarvah.mbg.rest.catalog.model;

import java.util.List;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

public class ProductCreateRequestParam extends AbstractDocument {

	private String skuid;

	private String name;

	private String longName;

	private String model;

	private String desc;

	private Set<String> subcategories;

	private Set<String> productTypes;

	private String productAssets;

	private Set<ProductAttribute> productAttributes;

	private String price;

	private String accessory;

	private List<String> relatedProducts;

	private List<String> accessories;

	private ProductAttribute features;

	private Set<ProductFaq> faqs;

	private String quantityType;

	// ProductBrand
	private String brandName;

	// Productattributes
	private String key;
	private String isMultiValued;
	private Set<String> values;
	// isMultivalued=false
	private String values2;

	// features
	private String key1;
	private String isMultiValued1;
	private Set<String> values1;
	// isMultivalued=false
	private String values3;

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

	public String getValues2() {
		return values2;
	}

	public void setValues2(String values2) {
		this.values2 = values2;
	}

	public String getValues3() {
		return values3;
	}

	public void setValues3(String values3) {
		this.values3 = values3;
	}

	private String answer;
	private String question;

	public String getIsMultiValued() {
		return isMultiValued;
	}

	public void setIsMultiValued(String isMultiValued) {
		this.isMultiValued = isMultiValued;
	}

	public String getIsMultiValued1() {
		return isMultiValued1;
	}

	public void setIsMultiValued1(String isMultiValued1) {
		this.isMultiValued1 = isMultiValued1;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String isMultiValued() {
		return isMultiValued;
	}

	public void setMultiValued(String isMultiValued) {
		this.isMultiValued = isMultiValued;
	}

	public Set<String> getValues() {
		return values;
	}

	public void setValues(Set<String> values) {
		this.values = values;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String isMultiValued1() {
		return isMultiValued1;
	}

	public void setMultiValued1(String isMultiValued1) {
		this.isMultiValued1 = isMultiValued1;
	}

	public Set<String> getValues1() {
		return values1;
	}

	public void setValues1(Set<String> values1) {
		this.values1 = values1;
	}

	public ProductAttribute getFeatures() {
		return features;
	}

	public void setFeatures(ProductAttribute features) {
		this.features = features;
	}

	public Set<ProductFaq> getFaqs() {
		return faqs;
	}

	public void setFaqs(Set<ProductFaq> faqs) {
		this.faqs = faqs;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<String> getProductTypes() {
		return productTypes;
	}

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

	public Set<String> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(Set<String> subcategories) {
		this.subcategories = subcategories;
	}

	public Set<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(Set<ProductAttribute> productAttributes) {
		this.productAttributes = productAttributes;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public List<String> getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(List<String> relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	public List<String> getAccessories() {
		return accessories;
	}

	public void setAccessories(List<String> accessories) {
		this.accessories = accessories;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
