/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

/**
 * @author Shiva
 *
 */
public class PricingResponse {

	private Double minPricing;

	private Double maxPricing;

	/**
	 * @return the minPricing
	 */
	public Double getMinPricing() {
		return minPricing;
	}

	/**
	 * @param minPricing
	 *            the minPricing to set
	 */
	public void setMinPricing(Double minPricing) {
		this.minPricing = minPricing;
	}

	/**
	 * @return the maxPricing
	 */
	public Double getMaxPricing() {
		return maxPricing;
	}

	/**
	 * @param maxPricing
	 *            the maxPricing to set
	 */
	public void setMaxPricing(Double maxPricing) {
		this.maxPricing = maxPricing;
	}

}
