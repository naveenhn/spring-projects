/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.Price;
import com.sarvah.mbg.domain.mongo.review.ConsolidatedRating;

/**
 * @author naveen
 *
 */
@Document
@CompoundIndexes({
	@CompoundIndex(name="rating_idx", def="{'consolidatedRating.avgRating.ratingVal': 1}"),
	@CompoundIndex(name="price_idx", def="{'price.value': 1}"),
	@CompoundIndex(name="prod_subcat_idx", def="{'subcategories.name': 1}")
	
	
})
public class Product extends AbstractDocument {

	@Indexed(unique = true)
	@NotNull(message = "Product skuid cannot be null")
	private String skuId;

	@TextIndexed
	@NotNull(message = "Product name cannot be null")
	private String name;

	@TextIndexed
	@NotNull(message = "Product model cannot be null")
	private String model;

    @TextIndexed
	private String longName; // long name of product if any

	// Textual description of product.
	@Indexed
	@NotNull(message = "Product desc cannot be null")
	private Description desc;

	@DBRef
	@NotNull(message = "Product brand cannot be null")
	private ProductBrand brand;

	@DBRef
	@NotNull(message = "Product quantity type cannot be null")
	private ProductQuantityType quantityType;

	@JsonIgnore
	@NotNull(message = "Product subcategories cannot be null")
	private Set<SubCategory> subcategories;

	@JsonIgnore
	private Set<ProductType> productTypes;

	// product related assets like images, videos and other files
	private ProductAsset assets;

	private ProductAttribute features;

	private Set<ProductAttribute> attributes;

	private Set<ProductFaq> faqs;

	private File tcDoc;

	@NotNull(message = "Product price cannot be null")
	private Price price;

	private boolean accessory;

	@JsonIgnore
	private Set<String> relatedProducts; // string = productids

	@JsonIgnore
	private Set<String> accessories; // string = productid

	@Indexed
	private ProductStatus status;

	
	private ConsolidatedRating consolidatedRating;

	// public Product(String id, String skuid, String name, Description desc,
	// Set<SubCategory> subcats, Set<ProductType> prodcutTypes, Asset
	// prodAssets, )

	

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
	 * @return the price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return the quantityType
	 */
	public ProductQuantityType getQuantityType() {
		return quantityType;
	}

	/**
	 * @param quantityType
	 *            the quantityType to set
	 */
	public void setQuantityType(ProductQuantityType quantityType) {
		this.quantityType = quantityType;
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
	 * @return the brand
	 */
	public ProductBrand getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	/**
	 * @return the consolidatedRating
	 */
	public ConsolidatedRating getConsolidatedRating() {
		return consolidatedRating;
	}

	/**
	 * @param consolidatedRating the consolidatedRating to set
	 */
	public void setConsolidatedRating(ConsolidatedRating consolidatedRating) {
		this.consolidatedRating = consolidatedRating;
	}

	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

}
