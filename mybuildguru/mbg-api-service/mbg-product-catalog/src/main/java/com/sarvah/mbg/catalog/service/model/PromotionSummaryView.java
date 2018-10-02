/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Date;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author Shiva
 *
 */
public class PromotionSummaryView {

	private String promoId;

	private String promotionName;

	// private String promotionType;

	private Date startDate;

	private Date endDate;

	private Integer noOfProducts;

	private Integer mainCategoryCount;

	private Integer brandCount;

	private Integer subCategoryCount;

	private Set<Product> products;

	private Set<String> mainCategory;

	private Set<SubCategory> subCategory;

	private Set<ProductBrand> brands;

	private String couponCode;

	private Double discountPercentage;

	private String status;

	/**
	 * @return the promoId
	 */
	public String getPromoId() {
		return promoId;
	}

	/**
	 * @param promoId
	 *            the promoId to set
	 */
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	/**
	 * @return the promotionName
	 */
	public String getPromotionName() {
		return promotionName;
	}

	/**
	 * @param promotionName
	 *            the promotionName to set
	 */
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the noOfProducts
	 */
	public Integer getNoOfProducts() {
		return noOfProducts;
	}

	/**
	 * @param noOfProducts
	 *            the noOfProducts to set
	 */
	public void setNoOfProducts(Integer noOfProducts) {
		this.noOfProducts = noOfProducts;
	}

	/**
	 * @return the mainCategoryCount
	 */
	public Integer getMainCategoryCount() {
		return mainCategoryCount;
	}

	/**
	 * @param mainCategoryCount
	 *            the mainCategoryCount to set
	 */
	public void setMainCategoryCount(Integer mainCategoryCount) {
		this.mainCategoryCount = mainCategoryCount;
	}

	/**
	 * @return the brandCount
	 */
	public Integer getBrandCount() {
		return brandCount;
	}

	/**
	 * @param brandCount
	 *            the brandCount to set
	 */
	public void setBrandCount(Integer brandCount) {
		this.brandCount = brandCount;
	}

	/**
	 * @return the subCategoryCount
	 */
	public Integer getSubCategoryCount() {
		return subCategoryCount;
	}

	/**
	 * @param subCategoryCount
	 *            the subCategoryCount to set
	 */
	public void setSubCategoryCount(Integer subCategoryCount) {
		this.subCategoryCount = subCategoryCount;
	}

	/**
	 * @return the products
	 */
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * @return the mainCategory
	 */
	public Set<String> getMainCategory() {
		return mainCategory;
	}

	/**
	 * @param mainCategory
	 *            the mainCategory to set
	 */
	public void setMainCategory(Set<String> mainCategory) {
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
	 * @return the brands
	 */
	public Set<ProductBrand> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(Set<ProductBrand> brands) {
		this.brands = brands;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the discountPercentage
	 */
	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage
	 *            the discountPercentage to set
	 */
	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
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
}
