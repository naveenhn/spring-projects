/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Date;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author shivu
 *
 */
public class ProductSummaryView {
	// provider
	private String productId;
	private String name;
	private String model;
	private String skuId;
	private Date createdDate;
	private Set<Category> mainCategory;
	private Set<SubCategory> subCategory;
	private String promotionsApplied;
	private ProductStatus status;

	// super admin
	private String providerName;
	private Integer numberOfDealers;

	// dealer
	private String brand;
	private Integer quantity;
	private Integer localEstimateDeliveryTime;
	private Integer zonalEstimateDeliveryTime;
	private Integer nationalEstimateDeliveryTime;

	private Double mrp;
	private Double SellingPrice;
	private Double rating;
	private String desc;
	private Double discount;
	private ProductAsset assets;

	/**
	 * @return the localEstimateDeliveryTime
	 */
	public Integer getLocalEstimateDeliveryTime() {
		return localEstimateDeliveryTime;
	}

	/**
	 * @param localEstimateDeliveryTime
	 *            the localEstimateDeliveryTime to set
	 */
	public void setLocalEstimateDeliveryTime(Integer localEstimateDeliveryTime) {
		this.localEstimateDeliveryTime = localEstimateDeliveryTime;
	}

	/**
	 * @return the zonalEstimateDeliveryTime
	 */
	public Integer getZonalEstimateDeliveryTime() {
		return zonalEstimateDeliveryTime;
	}

	/**
	 * @param zonalEstimateDeliveryTime
	 *            the zonalEstimateDeliveryTime to set
	 */
	public void setZonalEstimateDeliveryTime(Integer zonalEstimateDeliveryTime) {
		this.zonalEstimateDeliveryTime = zonalEstimateDeliveryTime;
	}

	/**
	 * @return the nationalEstimateDeliveryTime
	 */
	public Integer getNationalEstimateDeliveryTime() {
		return nationalEstimateDeliveryTime;
	}

	/**
	 * @param nationalEstimateDeliveryTime
	 *            the nationalEstimateDeliveryTime to set
	 */
	public void setNationalEstimateDeliveryTime(
			Integer nationalEstimateDeliveryTime) {
		this.nationalEstimateDeliveryTime = nationalEstimateDeliveryTime;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return SellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(Double sellingPrice) {
		SellingPrice = sellingPrice;
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
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the mainCategory
	 */
	public Set<Category> getMainCategory() {
		return mainCategory;
	}

	/**
	 * @param mainCategory
	 *            the mainCategory to set
	 */
	public void setMainCategory(Set<Category> mainCategory) {
		this.mainCategory = mainCategory;
	}

	/**
	 * @return the subCategory
	 */
	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

	/**
	 * @return the promotionsApplied
	 */
	public String getPromotionsApplied() {
		return promotionsApplied;
	}

	/**
	 * @param promotionsApplied
	 *            the promotionsApplied to set
	 */
	public void setPromotionsApplied(String promotionsApplied) {
		this.promotionsApplied = promotionsApplied;
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
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return the numberOfDealers
	 */
	public Integer getNumberOfDealers() {
		return numberOfDealers;
	}

	/**
	 * @param numberOfDealers
	 *            the numberOfDealers to set
	 */
	public void setNumberOfDealers(Integer numberOfDealers) {
		this.numberOfDealers = numberOfDealers;
	}

	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
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

}
