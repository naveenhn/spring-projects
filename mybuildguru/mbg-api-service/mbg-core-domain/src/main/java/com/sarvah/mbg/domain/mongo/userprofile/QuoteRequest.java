/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.catalog.QuoteStatus;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author Shivu
 *
 */
@Document
public class QuoteRequest extends AbstractDocument {
	@DBRef
	@NotNull
	@JsonIgnore
	private User customer;
	private String zipcode;
	@DBRef
	@NotNull
	private List<QuoteRequestProduct> products;
	private String buildOnnQuoteRequestId;
	private QuoteStatus status;
	private String deliveryDate;
	private String note;
	private String followupDate;
	private String reasonForReject;
	private boolean quoteSellingPriceIncludeTax;

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
	public List<QuoteRequestProduct> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<QuoteRequestProduct> products) {
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
	public QuoteStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(QuoteStatus status) {
		this.status = status;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
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
