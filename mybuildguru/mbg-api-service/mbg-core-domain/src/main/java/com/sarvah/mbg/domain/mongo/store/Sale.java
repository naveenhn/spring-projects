/**
 * 
 */
package com.sarvah.mbg.domain.mongo.store;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Sale.
 *
 * @author naveen
 */
public class Sale {
	
	/** The sale price. */
	private double salePrice;
	
	/** The sale start date. */
	private Date saleStartDate;
	
	/**
	 * Gets the sale price.
	 *
	 * @return the salePrice
	 */
	public double getSalePrice() {
		return salePrice;
	}
	
	/**
	 * Sets the sale price.
	 *
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	/**
	 * Gets the sale start date.
	 *
	 * @return the saleStartDate
	 */
	public Date getSaleStartDate() {
		return saleStartDate;
	}
	
	/**
	 * Sets the sale start date.
	 *
	 * @param saleStartDate the saleStartDate to set
	 */
	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

}
