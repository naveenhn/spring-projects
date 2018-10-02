/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Set;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductAttribute;
import com.sarvah.mbg.domain.mongo.catalog.ProductFaq;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.ProductType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;

/**
 * @author Shiva
 *
 */
public class ProductCatalogDetails {

	private String id;

	private String skuid;

	private String name;

	private String fullName;

	private String model;

	private String longName; // long name of product if any

	private Description desc;

	private String brand;

	private ProductAsset assets;

	private ProductAttribute features;

	private Set<ProductAttribute> attributes;

	private Set<ProductFaq> faqs;

	private File tcDoc;

	private boolean accessory;

	private ProductStatus status;

	private ConsolidatedRating rating;

	private Price price;

	private Double mrp;

	private Double sellingPrice;

	private Double discount;

	private Set<ProductType> types;

	private Set<SubCategory> subCategories;

	private boolean isSeller;

	private String quantityType;

	/**
	 * @return the types
	 */
	public Set<ProductType> getTypes() {
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(Set<ProductType> types) {
		this.types = types;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return the skuid
	 */
	public String getSkuid() {
		return skuid;
	}

	/**
	 * @param skuid
	 *            the skuid to set
	 */
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

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
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
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
	 * @return the accessory
	 */
	public boolean isAccessory() {
		return accessory;
	}

	/**
	 * @param accessory
	 *            the accessory to set
	 */
	public void setAccessory(boolean accessory) {
		this.accessory = accessory;
	}

	/**
	 * @return the status
	 */
	public ProductStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ProductStatus status) {
		this.status = status;
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
	 * @return the mrp
	 */
	public Double getMrp() {
		return mrp;
	}

	/**
	 * @param mrp
	 *            the mrp to set
	 */
	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the subCategories
	 */
	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories
	 *            the subCategories to set
	 */
	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

	/**
	 * @return the isSeller
	 */
	public boolean isSeller() {
		return isSeller;
	}

	/**
	 * @param isSeller
	 *            the isSeller to set
	 */
	public void setSeller(boolean isSeller) {
		this.isSeller = isSeller;
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
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}