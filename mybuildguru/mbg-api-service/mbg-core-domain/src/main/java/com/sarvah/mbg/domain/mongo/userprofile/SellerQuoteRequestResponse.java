/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Set;

/**
 * @author Shivu
 *
 */
public class SellerQuoteRequestResponse {
	private String id;
	private Set<SellerQuoteProductPricing> products;
	private User seller;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the products
	 */
	public Set<SellerQuoteProductPricing> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(Set<SellerQuoteProductPricing> products) {
		this.products = products;
	}

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
}
