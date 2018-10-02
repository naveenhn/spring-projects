/**
 * 
 */
package com.sarvah.mbg.domain.mongo.marketing;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class Promotion extends AbstractDocument {

	@NotNull(message = "Promotion name cannot be null")
	private String name;
	
	//@NotNull(message = "Promotion desc cannot be null")
	private Description desc;

	//@NotNull(message = "Promotion type cannot be null")
	private PromotionType type;

	// DBRef or
	@JsonIgnore
	// @NotNull(message = "Promotion dealer cannot be null")
	@DBRef
	private User dealer;

	// promotions on products
	private Set<String> productIds;

	// private Categories category; // For Promo code

	private Set<SubCategory> subCategory; // For Promo code

	// @Indexed(unique = true)
	@NotNull(message = "Promo code cannot be null")
	private String promoCode; // For Promo code

	@NotNull(message = "Discount cannot be null")
	private Double discount;

	private Date startDate;

	private Date endDate;

	private boolean active;

	private Set<ProductBrand> brands;

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
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
	 * @return the type
	 */
	public PromotionType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(PromotionType type) {
		this.type = type;
	}

	/**
	 * @return the dealer
	 */
	public User getDealer() {
		return dealer;
	}

	/**
	 * @param dealer
	 *            the dealer to set
	 */
	public void setDealer(User dealer) {
		this.dealer = dealer;
	}

	/**
	 * @return the productIds
	 */
	public Set<String> getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds
	 *            the productIds to set
	 */
	public void setProductIds(Set<String> productIds) {
		this.productIds = productIds;
	}

	// /**
	// * @return the category
	// */
	// public Categories getCategory() {
	// return category;
	// }
	//
	// /**
	// * @param category
	// * the category to set
	// */
	// public void setCategory(Categories category) {
	// this.category = category;
	// }

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
	 * @return the promoCode
	 */
	public String getPromoCode() {
		return promoCode;
	}

	/**
	 * @param promoCode
	 *            the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
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
}
