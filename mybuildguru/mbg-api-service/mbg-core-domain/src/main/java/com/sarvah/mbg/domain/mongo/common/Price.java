/**
 * 
 */
package com.sarvah.mbg.domain.mongo.common;

import javax.validation.constraints.NotNull;

/**
 * @author naveen
 *
 */
public class Price {
	
	@NotNull (message = "Pricing currency cannot be null")
	private String currency;
	@NotNull(message="Pricing mrp cannot be null")
	private double mrp;
	private double selling;
	private double savings;
	private double pctSavings;
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the mrp
	 */
	public double getMrp() {
		return mrp;
	}
	/**
	 * @param mrp the mrp to set
	 */
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	/**
	 * @return the selling
	 */
	public double getSelling() {
		return selling;
	}
	/**
	 * @param selling the selling to set
	 */
	public void setSelling(double selling) {
		this.selling = selling;
	}
	/**
	 * @return the savings
	 */
	public double getSavings() {
		return savings;
	}
	/**
	 * @param savings the savings to set
	 */
	public void setSavings(double savings) {
		this.savings = savings;
	}
	/**
	 * @return the pctSavings
	 */
	public double getPctSavings() {
		return pctSavings;
	}
	/**
	 * @param pctSavings the pctSavings to set
	 */
	public void setPctSavings(double pctSavings) {
		this.pctSavings = pctSavings;
	}
	
}
