/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Date;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.Category;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author shivu
 *
 */
public class ProductDetailView {
	private String pId;
	private String model;
	private String skuid;
	private String name;
	private ProductQuantityType quantityType;
	private Set<Category> mainCategory;
	private Set<SubCategory> subCategory;
	private String packageName;
	private Date onboardedDate;
	private Integer numberOfDealers;
	private Integer numberOfLocations;
	private Integer numberOfCities;
	private String status;
	private String onboardedBy;
	private String brand;
	private Integer qty;
	private double mrp;
	private double sellingPrice;
	private Integer minLocalEstimateDeliveryTime;
	private Integer minZonalEstimateDeliveryTime;
	private Integer minNationalEstimateDeliveryTime;
	private Integer maxLocalEstimateDeliveryTime;
	private Integer maxZonalEstimateDeliveryTime;
	private Integer maxNationalEstimateDeliveryTime;
	private double localEstimateDeliveryChrg;
	private double zonalEstimateDeliveryChrg;
	private double nationalEstimateDeliveryChrg;
	private Integer minQuantityBuy;
	private String promotionApplied;
	private double mbgShare;
	private boolean isLocalRegion;
	private boolean isZonalRegion;
	private boolean isNationalRegion;
	private boolean linkedToMe;
	private double discount;
	private boolean willYouDeliver;
	// dealer
	private Set<String> length;
	private Set<String> breadth;
	private Set<String> height;
	private Set<String> weight;

	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}

	/**
	 * @param pId
	 *            the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
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
	 * @return the length
	 */
	public Set<String> getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(Set<String> length) {
		this.length = length;
	}

	/**
	 * @return the breadth
	 */
	public Set<String> getBreadth() {
		return breadth;
	}

	/**
	 * @param breadth
	 *            the breadth to set
	 */
	public void setBreadth(Set<String> breadth) {
		this.breadth = breadth;
	}

	/**
	 * @return the height
	 */
	public Set<String> getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Set<String> height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public Set<String> getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(Set<String> weight) {
		this.weight = weight;
	}

	/**
	 * @return the minQuantityBuy
	 */
	public Integer getMinQuantityBuy() {
		return minQuantityBuy;
	}

	/**
	 * @param minQuantityBuy
	 *            the minQuantityBuy to set
	 */
	public void setMinQuantityBuy(Integer minQuantityBuy) {
		this.minQuantityBuy = minQuantityBuy;
	}

	/**
	 * @return the localEstimateDeliveryChrg
	 */
	public double getLocalEstimateDeliveryChrg() {
		return localEstimateDeliveryChrg;
	}

	/**
	 * @param localEstimateDeliveryChrg
	 *            the localEstimateDeliveryChrg to set
	 */
	public void setLocalEstimateDeliveryChrg(double localEstimateDeliveryChrg) {
		this.localEstimateDeliveryChrg = localEstimateDeliveryChrg;
	}

	/**
	 * @return the zonalEstimateDeliveryChrg
	 */
	public double getZonalEstimateDeliveryChrg() {
		return zonalEstimateDeliveryChrg;
	}

	/**
	 * @param zonalEstimateDeliveryChrg
	 *            the zonalEstimateDeliveryChrg to set
	 */
	public void setZonalEstimateDeliveryChrg(double zonalEstimateDeliveryChrg) {
		this.zonalEstimateDeliveryChrg = zonalEstimateDeliveryChrg;
	}

	/**
	 * @return the nationalEstimateDeliveryChrg
	 */
	public double getNationalEstimateDeliveryChrg() {
		return nationalEstimateDeliveryChrg;
	}

	/**
	 * @param nationalEstimateDeliveryChrg
	 *            the nationalEstimateDeliveryChrg to set
	 */
	public void setNationalEstimateDeliveryChrg(
			double nationalEstimateDeliveryChrg) {
		this.nationalEstimateDeliveryChrg = nationalEstimateDeliveryChrg;
	}

	public String getOnboardedBy() {
		return onboardedBy;
	}

	public void setOnboardedBy(String onboardedBy) {
		this.onboardedBy = onboardedBy;
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
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the onboardedDate
	 */
	public Date getOnboardedDate() {
		return onboardedDate;
	}

	/**
	 * @param onboardedDate
	 *            the onboardedDate to set
	 */
	public void setOnboardedDate(Date onboardedDate) {
		this.onboardedDate = onboardedDate;
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
	 * @return the numberOfLocations
	 */
	public Integer getNumberOfLocations() {
		return numberOfLocations;
	}

	/**
	 * @param numberOfLocations
	 *            the numberOfLocations to set
	 */
	public void setNumberOfLocations(Integer numberOfLocations) {
		this.numberOfLocations = numberOfLocations;
	}

	/**
	 * @return the numberOfCities
	 */
	public Integer getNumberOfCities() {
		return numberOfCities;
	}

	/**
	 * @param numberOfCities
	 *            the numberOfCities to set
	 */
	public void setNumberOfCities(Integer numberOfCities) {
		this.numberOfCities = numberOfCities;
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
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the mrp
	 */
	public double getMrp() {
		return mrp;
	}

	/**
	 * @param mrp
	 *            the mrp to set
	 */
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the sellingPrice
	 */
	public double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the promotionApplied
	 */
	public String getPromotionApplied() {
		return promotionApplied;
	}

	/**
	 * @param promotionApplied
	 *            the promotionApplied to set
	 */
	public void setPromotionApplied(String promotionApplied) {
		this.promotionApplied = promotionApplied;
	}

	/**
	 * @return the mbgShare
	 */
	public double getMbgShare() {
		return mbgShare;
	}

	/**
	 * @param mbgShare
	 *            the mbgShare to set
	 */
	public void setMbgShare(double mbgShare) {
		this.mbgShare = mbgShare;
	}

	/**
	 * @return the isLocalRegion
	 */
	public boolean isLocalRegion() {
		return isLocalRegion;
	}

	/**
	 * @param isLocalRegion
	 *            the isLocalRegion to set
	 */
	public void setLocalRegion(boolean isLocalRegion) {
		this.isLocalRegion = isLocalRegion;
	}

	/**
	 * @return the isZonalRegion
	 */
	public boolean isZonalRegion() {
		return isZonalRegion;
	}

	/**
	 * @param isZonalRegion
	 *            the isZonalRegion to set
	 */
	public void setZonalRegion(boolean isZonalRegion) {
		this.isZonalRegion = isZonalRegion;
	}

	/**
	 * @return the isNationalRegion
	 */
	public boolean isNationalRegion() {
		return isNationalRegion;
	}

	/**
	 * @param isNationalRegion
	 *            the isNationalRegion to set
	 */
	public void setNationalRegion(boolean isNationalRegion) {
		this.isNationalRegion = isNationalRegion;
	}

	/**
	 * @return the minLocalEstimateDeliveryTime
	 */
	public Integer getMinLocalEstimateDeliveryTime() {
		return minLocalEstimateDeliveryTime;
	}

	/**
	 * @param minLocalEstimateDeliveryTime
	 *            the minLocalEstimateDeliveryTime to set
	 */
	public void setMinLocalEstimateDeliveryTime(
			Integer minLocalEstimateDeliveryTime) {
		this.minLocalEstimateDeliveryTime = minLocalEstimateDeliveryTime;
	}

	/**
	 * @return the minZonalEstimateDeliveryTime
	 */
	public Integer getMinZonalEstimateDeliveryTime() {
		return minZonalEstimateDeliveryTime;
	}

	/**
	 * @param minZonalEstimateDeliveryTime
	 *            the minZonalEstimateDeliveryTime to set
	 */
	public void setMinZonalEstimateDeliveryTime(
			Integer minZonalEstimateDeliveryTime) {
		this.minZonalEstimateDeliveryTime = minZonalEstimateDeliveryTime;
	}

	/**
	 * @return the minNationalEstimateDeliveryTime
	 */
	public Integer getMinNationalEstimateDeliveryTime() {
		return minNationalEstimateDeliveryTime;
	}

	/**
	 * @param minNationalEstimateDeliveryTime
	 *            the minNationalEstimateDeliveryTime to set
	 */
	public void setMinNationalEstimateDeliveryTime(
			Integer minNationalEstimateDeliveryTime) {
		this.minNationalEstimateDeliveryTime = minNationalEstimateDeliveryTime;
	}

	/**
	 * @return the maxLocalEstimateDeliveryTime
	 */
	public Integer getMaxLocalEstimateDeliveryTime() {
		return maxLocalEstimateDeliveryTime;
	}

	/**
	 * @param maxLocalEstimateDeliveryTime
	 *            the maxLocalEstimateDeliveryTime to set
	 */
	public void setMaxLocalEstimateDeliveryTime(
			Integer maxLocalEstimateDeliveryTime) {
		this.maxLocalEstimateDeliveryTime = maxLocalEstimateDeliveryTime;
	}

	/**
	 * @return the maxZonalEstimateDeliveryTime
	 */
	public Integer getMaxZonalEstimateDeliveryTime() {
		return maxZonalEstimateDeliveryTime;
	}

	/**
	 * @param maxZonalEstimateDeliveryTime
	 *            the maxZonalEstimateDeliveryTime to set
	 */
	public void setMaxZonalEstimateDeliveryTime(
			Integer maxZonalEstimateDeliveryTime) {
		this.maxZonalEstimateDeliveryTime = maxZonalEstimateDeliveryTime;
	}

	/**
	 * @return the maxNationalEstimateDeliveryTime
	 */
	public Integer getMaxNationalEstimateDeliveryTime() {
		return maxNationalEstimateDeliveryTime;
	}

	/**
	 * @param maxNationalEstimateDeliveryTime
	 *            the maxNationalEstimateDeliveryTime to set
	 */
	public void setMaxNationalEstimateDeliveryTime(
			Integer maxNationalEstimateDeliveryTime) {
		this.maxNationalEstimateDeliveryTime = maxNationalEstimateDeliveryTime;
	}

	/**
	 * @return the linkedToMe
	 */
	public boolean isLinkedToMe() {
		return linkedToMe;
	}

	/**
	 * @param linkedToMe
	 *            the linkedToMe to set
	 */
	public void setLinkedToMe(boolean linkedToMe) {
		this.linkedToMe = linkedToMe;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @return the willYouDeliver
	 */
	public boolean isWillYouDeliver() {
		return willYouDeliver;
	}

	/**
	 * @param willYouDeliver
	 *            the willYouDeliver to set
	 */
	public void setWillYouDeliver(boolean willYouDeliver) {
		this.willYouDeliver = willYouDeliver;
	}
}
