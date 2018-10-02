/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

/**
 * @author shivu s
 *
 */
public class PriceResponse {
	private Double sellingPrice;
	private Double mbgDiscountedPrice;
	private Double savings;
	private String promoName;
	private String promoCode;

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
	 * @return the mbgDiscountedPrice
	 */
	public Double getMbgDiscountedPrice() {
		return mbgDiscountedPrice;
	}

	/**
	 * @param mbgDiscountedPrice
	 *            the mbgDiscountedPrice to set
	 */
	public void setMbgDiscountedPrice(Double mbgDiscountedPrice) {
		this.mbgDiscountedPrice = mbgDiscountedPrice;
	}

	/**
	 * @return the savings
	 */
	public Double getSavings() {
		return savings;
	}

	/**
	 * @param savings
	 *            the savings to set
	 */
	public void setSavings(Double savings) {
		this.savings = savings;
	}

	/**
	 * @return the promoName
	 */
	public String getPromoName() {
		return promoName;
	}

	/**
	 * @param promoName
	 *            the promoName to set
	 */
	public void setPromoName(String promoName) {
		this.promoName = promoName;
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
}
