/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.List;

/**
 * @author Shivu
 *
 */
public class SellerQuoteProductResponse {
	private User seller;
	private List<QuoteProductResponse> products;
	private Double shippingCharge;
	private Double extraBenefits;

	/**
	 * @return the seller
	 */
	public User getSeller() {
		return seller;
	}

	/**
	 * @param seller
	 *            the seller to set
	 */
	public void setSeller(User seller) {
		this.seller = seller;
	}

	/**
	 * @return the products
	 */
	public List<QuoteProductResponse> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<QuoteProductResponse> products) {
		this.products = products;
	}

	public Double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	/**
	 * @return the extraBenefits
	 */
	public Double getExtraBenefits() {
		return extraBenefits;
	}

	/**
	 * @param extraBenefits
	 *            the extraBenefits to set
	 */
	public void setExtraBenefits(Double extraBenefits) {
		this.extraBenefits = extraBenefits;
	}

}
