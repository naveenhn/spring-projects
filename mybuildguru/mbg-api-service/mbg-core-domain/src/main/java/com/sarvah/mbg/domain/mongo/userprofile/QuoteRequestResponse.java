/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Date;
import java.util.List;

/**
 * @author Shivu
 *
 */
public class QuoteRequestResponse {
	private String id;
	private User customer;
	private String zipcode;
	private List<SellerQuoteProductResponse> products;
	private String buildOnnQuoteRequestId;
	private String status;
	private String deliveryDate;
	private Date createdDate;
	private Double buildOnnShippingCharge;
	private String note;
	private String followupDate;
	private String reasonForReject;
	private boolean quoteSellingPriceIncludeTax;

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
	 * @return the customer
	 */
	public User getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(User customer) {
		this.customer = customer;
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
	public List<SellerQuoteProductResponse> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<SellerQuoteProductResponse> products) {
		this.products = products;
	}

	/**
	 * @return the buildOnnQuoteRequestId
	 */
	public String getBuildOnnQuoteRequestId() {
		return buildOnnQuoteRequestId;
	}

	/**
	 * @param buildOnnQuoteRequestId
	 *            the buildOnnQuoteRequestId to set
	 */
	public void setBuildOnnQuoteRequestId(String buildOnnQuoteRequestId) {
		this.buildOnnQuoteRequestId = buildOnnQuoteRequestId;
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

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public boolean isQuoteSellingPriceIncludeTax() {
		return quoteSellingPriceIncludeTax;
	}

	/**
	 * @param quoteSellingPriceIncludeTax
	 *            the quoteSellingPriceIncludeTax to set
	 */
	public void setQuoteSellingPriceIncludeTax(
			boolean quoteSellingPriceIncludeTax) {
		this.quoteSellingPriceIncludeTax = quoteSellingPriceIncludeTax;
	}

}
