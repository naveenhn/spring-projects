/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

import com.sarvah.mbg.userprofile.response.QuoteProductRequestParam;

/**
 * @author Shivu
 *
 */
public class QuoteCreateRequestParam {
	private String customerId;
	private String zipcode;
	private List<QuoteProductRequestParam> products;
	private String bonnQuoteRequestId;
	private String deliveryDate;
	private Double buildOnnShippingCharge;
	private String note;
	private String alertType;
	private String followupDate;
	private String reasonForReject;
	private String quoteSellingPriceIncludeTax;

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the products
	 */
	public List<QuoteProductRequestParam> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<QuoteProductRequestParam> products) {
		this.products = products;
	}

	public String getBonnQuoteRequestId() {
		return bonnQuoteRequestId;
	}

	public void setBonnQuoteRequestId(String bonnQuoteRequestId) {
		this.bonnQuoteRequestId = bonnQuoteRequestId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the buildOnnShippingCharge
	 */
	public Double getBuildOnnShippingCharge() {
		return buildOnnShippingCharge;
	}

	/**
	 * @param buildOnnShippingCharge
	 *            the buildOnnShippingCharge to set
	 */
	public void setBuildOnnShippingCharge(Double buildOnnShippingCharge) {
		this.buildOnnShippingCharge = buildOnnShippingCharge;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the alertType
	 */
	public String getAlertType() {
		return alertType;
	}

	/**
	 * @param alertType
	 *            the alertType to set
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	/**
	 * @return the followupDate
	 */
	public String getFollowupDate() {
		return followupDate;
	}

	/**
	 * @param followupDate
	 *            the followupDate to set
	 */
	public void setFollowupDate(String followupDate) {
		this.followupDate = followupDate;
	}

	/**
	 * @return the reasonForReject
	 */
	public String getReasonForReject() {
		return reasonForReject;
	}

	/**
	 * @param reasonForReject
	 *            the reasonForReject to set
	 */
	public void setReasonForReject(String reasonForReject) {
		this.reasonForReject = reasonForReject;
	}

	/**
	 * @return the quoteSellingPriceIncludeTax
	 */
	public String getQuoteSellingPriceIncludeTax() {
		return quoteSellingPriceIncludeTax;
	}

	/**
	 * @param quoteSellingPriceIncludeTax
	 *            the quoteSellingPriceIncludeTax to set
	 */
	public void setQuoteSellingPriceIncludeTax(
			String quoteSellingPriceIncludeTax) {
		this.quoteSellingPriceIncludeTax = quoteSellingPriceIncludeTax;
	}
}
